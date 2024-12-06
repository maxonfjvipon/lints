<?xml version="1.0" encoding="UTF-8"?>
<!--
The MIT License (MIT)

Copyright (c) 2016-2024 Objectionary.com

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
-->
<xsl:stylesheet xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" id="schema-is-absent" version="2.0">
  <xsl:output encoding="UTF-8" method="xml"/>
  <xsl:template match="/">
    <defects>
      <xsl:apply-templates select="/program[not(@xsi:noNamespaceSchemaLocation)]"/>
    </defects>
  </xsl:template>
  <xsl:template match="/program[not(@xsi:noNamespaceSchemaLocation)]">
    <xsl:element name="defect">
      <xsl:attribute name="line">
        <xsl:text>0</xsl:text>
      </xsl:attribute>
      <xsl:attribute name="severity">
        <xsl:text>critical</xsl:text>
      </xsl:attribute>
      <xsl:text>There is no XSD schema attached to the XML document, </xsl:text>
      <xsl:text>while xsi:noNamespaceSchemaLocation is expected</xsl:text>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
