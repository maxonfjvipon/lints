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

import com.jcabi.manifests.Manifests;

/**
 * A single defect found.
 *
 * @since 0.0.1
 */
public interface Defect {

    /**
     * Rule.
     * @return Unique name of the rule
     */
    String rule();

    /**
     * Severity.
     * @return Severity
     */
    Severity severity();

    /**
     * Line where it was found.
     * @return Line number
     */
    int line();

    /**
     * Error message.
     * @return Text
     */
    String text();

    /**
     * The linter's current version.
     * @return Linter's current version
     */
    String version();

    /**
     * Default.
     *
     * @since 0.0.1
     */
    final class Default implements Defect {
        /**
         * Rule.
         */
        private final String rle;

        /**
         * Severity.
         */
        private final Severity sev;

        /**
         * Line number with the defect.
         */
        private final int lineno;

        /**
         * The message of the problem.
         */
        private final String txt;

        /**
         * Ctor.
         * @param rule Rule
         * @param severity Severity
         * @param line Line number
         * @param text Description of the defect
         * @checkstyle ParameterNumberCheck (5 lines)
         */
        public Default(final String rule, final Severity severity,
            final int line, final String text) {
            this.rle = rule;
            this.sev = severity;
            this.lineno = line;
            this.txt = text;
        }

        @Override
        public String toString() {
            return String.format(
                "[%s %s]:%d %s", this.rle, this.sev, this.lineno, this.txt
            );
        }

        @Override
        public String rule() {
            return this.rle;
        }

        @Override
        public Severity severity() {
            return this.sev;
        }

        @Override
        public int line() {
            return this.lineno;
        }

        @Override
        public String text() {
            return this.txt;
        }

        @Override
        public String version() {
            return Manifests.read("Lints-Version");
        }
    }

}
