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

import com.jcabi.xml.XML;
import java.io.IOException;
import java.util.Objects;
import javax.annotation.concurrent.ThreadSafe;
import org.cactoos.Scalar;
import org.cactoos.scalar.IoChecked;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Synced;

/**
 * Lints to use.
 * This class is thread-safe.
 * @since 0.23
 * @todo #148:30min Refactor {@link Lints},{@link ProgramLints} and {@link XslLints} classes.
 *  We already have ProgramLints and XslLints. Now, we will also have Lints.
 *  We can we put this functionality into ProgramLints.
 */
@ThreadSafe
final class Lints {

    /**
     * Default lints.
     */
    private static final Scalar<Iterable<Lint<XML>>> DEFAULT = new Sticky<>(
        () -> new ProgramLints().value()
    );

    /**
     * All lints.
     */
    private final Scalar<Iterable<Lint<XML>>> all;

    /**
     * Default ctor.
     */
    Lints() {
        this(Lints.DEFAULT);
    }

    /**
     * Ctor.
     * @param all All lints.
     */
    Lints(final Scalar<Iterable<Lint<XML>>> all) {
        this.all = new Synced<>(all);
    }

    /**
     * Iterate over lints.
     * Pay attention that this method can throw an exception.
     * This is why we don't inherit from {@link Iterable}.
     * @return Lints
     * @throws IOException If fails
     */
    Iterable<Lint<XML>> iterator() throws IOException {
        final Iterable<Lint<XML>> res = new IoChecked<>(this.all).value();
        if (Objects.isNull(res)) {
            throw new IllegalStateException(
                String.format("Lints are null before iteration in the %s", this)
            );
        }
        return res;
    }
}
