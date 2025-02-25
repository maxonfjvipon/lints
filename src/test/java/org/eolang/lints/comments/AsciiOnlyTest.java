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
package org.eolang.lints.comments;

import java.io.IOException;
import java.util.Collection;
import org.cactoos.io.InputOf;
import org.cactoos.list.ListOf;
import org.eolang.lints.Defect;
import org.eolang.parser.EoSyntax;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link AsciiOnly}.
 *
 * @since 0.0.1
 */
final class AsciiOnlyTest {

    @Test
    void catchesNonAsciiInComment() throws IOException {
        final Collection<Defect> defects = new AsciiOnly().defects(
            new EoSyntax(
                new InputOf("# привет\n# как дела?\n[] > foo\n")
            ).parsed()
        );
        MatcherAssert.assertThat(
            "non-ascii comment is not welcome",
            defects,
            Matchers.hasSize(Matchers.greaterThan(0))
        );
        MatcherAssert.assertThat(
            "non-ascii comment error should contain abusive character",
            new ListOf<>(defects).get(0).text(),
            Matchers.is(
                "Only ASCII characters are allowed in comments, while 'п' is used at the 3th line at the 1th position"
            )
        );
    }

    @Test
    void explainsMotive() throws Exception {
        MatcherAssert.assertThat(
            "The motive doesn't contain expected string",
            new AsciiOnly().motive().contains("# ASCII-Only Characters in Comment"),
            new IsEqual<>(true)
        );
    }

}
