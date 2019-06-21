# Java Internationalization (I18n) Rules for SonarQube


## Installation

Go to Administration > Marketplace > Search for "Java I18n" > Install > Restart the SonarQube server

## Usage

This plugin is a Java project analyzer, the way to use it is the same as [SonarJava](https://docs.sonarqube.org/display/PLUG/SonarJava).

## Rule Set Information

The rule set of this plugin for internationalization static code analysis focus on key internationalization issues -- particularly those issues that can cause more serious bugs in code. Plugin coverage includes internationalization issues around input/output, locales, formatting (dates, times, numbers, etc.), sorting, etc.

- Rules V1002-1008 check for potential i18n issues with input/output.
- Rules V1010-1020 relate to locales. Many internationalization pieces, such as formatting, sorting, line-breaking, and more, depend on locales.
- Rules V1009-v1011 relate to date/time formatting issues.
- Rules V1014-V1018 relate to linguistic sorting/collation.

More details about the rules, the exact APIs checked, as well as suggested code fixes for resolution can be found in the HTML files that are part of this plugin.

## Feedback

If you find a bug or wish to add another Java i18n rule, please open a [GitHub issue](https://github.com/edwinyangzh/java-i18n/issues).
