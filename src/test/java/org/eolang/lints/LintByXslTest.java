/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2024 Objectionary.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.eolang.lints;

import com.yegor256.MayBeSlow;
import com.yegor256.WeAreOnline;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import org.cactoos.io.InputOf;
import org.eolang.jucs.ClasspathSource;
import org.eolang.parser.CheckPack;
import org.eolang.parser.EoSyntax;
import org.eolang.xax.XaxStory;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;

/**
 * Test for {@link LintByXsl}.
 *
 * @since 0.0.1
 */
final class LintByXslTest {

    @Test
    void lintsOneFile() throws IOException {
        MatcherAssert.assertThat(
            "the objects is found",
            new LintByXsl("critical/duplicate-names").defects(
                new EoSyntax(
                    new InputOf("# first\n[] > foo\n# first\n[] > foo\n")
                ).parsed()
            ),
            Matchers.hasSize(Matchers.greaterThan(0))
        );
    }

    @ParameterizedTest
    @ExtendWith(MayBeSlow.class)
    @ExtendWith(WeAreOnline.class)
    @ClasspathSource(value = "org/eolang/lints/eo-packs/", glob = "**.yaml")
    void testsAllLintsByEo(final String pack) throws IOException {
        final CheckPack check = new CheckPack(pack);
        if (check.skip()) {
            Assumptions.abort(String.format("%s is not ready", pack));
        }
        MatcherAssert.assertThat(
            String.format("The check pack has failed: %n%s", pack),
            check.failures(),
            Matchers.empty()
        );
    }

    @ParameterizedTest
    @ExtendWith(MayBeSlow.class)
    @ExtendWith(WeAreOnline.class)
    @ClasspathSource(value = "org/eolang/lints/xmir-packs/", glob = "**.yaml")
    void testsAllLintsByXmir(final String yaml) {
        MatcherAssert.assertThat(
            String.format("The %s xax check is failed", yaml),
            new XaxStory(yaml),
            Matchers.is(true)
        );
    }

    @Test
    void returnsMotive() throws Exception {
        MatcherAssert.assertThat(
            "The motive was not found or empty",
            new LintByXsl("critical/duplicate-names").motive().isEmpty(),
            new IsEqual<>(false)
        );
    }

    @Test
    void testLocationsOfYamlPacks() throws IOException {
        final Set<String> groups = Files.walk(Paths.get("src/main/resources/org/eolang/lints"))
            .filter(Files::isRegularFile)
            .filter(path -> path.toString().endsWith(".xsl"))
            .map(path -> path.getParent().getFileName().toString())
            .collect(Collectors.toSet());
        Files.walk(Paths.get("src/test/resources/org/eolang/lints/eo-packs"))
            .filter(Files::isRegularFile)
            .forEach(
                path -> {
                    final String lint = path.getParent().getFileName().toString();
                    final Path folder = Paths.get("src/main/resources/org/eolang/lints/");
                    boolean found = false;
                    for (final String group : groups) {
                        final Path xsl = folder.resolve(group).resolve(
                            String.format("%s.xsl", lint)
                        );
                        if (Files.exists(xsl)) {
                            found = true;
                            break;
                        }
                    }
                    MatcherAssert.assertThat(
                        String.format(
                            "Can't find XSL for %s/%s, which must be located at %s",
                            lint, path.getFileName(), folder
                        ),
                        found,
                        new IsEqual<>(true)
                    );
                }
            );
    }

    @Test
    void checksFileNaming() throws IOException {
        final String[] folders = {"eo-packs", "xmir-packs"};
        for (final String folder : folders) {
            Files.walk(Paths.get("src/test/resources/org/eolang/lints").resolve(folder))
                .filter(Files::isRegularFile)
                .forEach(
                    path -> MatcherAssert.assertThat(
                        String.format("Only YAML files are allowed here, while: %s", path),
                        path.toFile().toString().endsWith(".yaml"),
                        new IsEqual<>(true)
                    )
                );
        }
    }

}
