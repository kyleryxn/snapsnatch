# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## **v0.2.0-SNAPSHOT - 2024-03-19**

### Added
- **GitHub Actions Workflow**: Implemented a "Release Workflow" to automate the release process following successful 
CI builds, enhancing the deployment efficiency and consistency.

### Changed
- Renamed `Parser` interface to `ContentParser` to better reflect its role.
- Renamed `HTMLParser` and `RobotsTxtParser` to `HTMLContentParser` and `RobotsTxtContentParser`, aligning with the new 
interface name.
- Moved methods for extracting images, links, and robots.txt directives into the `ContentParser` interface, providing 
default implementations to ensure consistency and reduce duplication across implementations.
- Updated the `getContentType` method in `ContentParser` to return a `Content` enum type, facilitating easier 
identification of parser types in `ParserService`.
- Re-engineered `ParserService` to use a map of `Content` to `ContentParser`, streamlining the parser selection process 
based on a content type. This change enables more dynamic parsing strategies and cleaner code structure.

### Deprecated
- No deprecations in this release.

### Removed
- No features or functionalities removed in this release.

### Fixed
- No bug fixes in this release.

### Security
- No specific security updates in this release.

### Documentation
- No significant documentation changes in this release.

### Miscellaneous
- No miscellaneous changes in this release.

This release marks a significant transition in the project's development, focusing on major upgrades and architectural
changes.

These changes enhance the flexibility, readability, and maintainability of the parsing system,
allowing for more scalable and modular content processing within the crawler.

## **v0.1.0-SNAPSHOT - 2024-03-16**

### Added
- No new features in this release.

### Changed
- Transitioned from multithreading with platform threads to virtual threads to optimize resource utilization and scalability.
- Upgraded from Java 17 to Java 21.
- Upgraded the application framework to Spring Boot from Spring Web MVC and Jakarta EE, reflecting a shift towards a modernized application architecture.

### Deprecated
- No deprecations in this release.

### Removed
- No features or functionalities removed in this release.

### Fixed
- No bug fixes in this release.

### Security
- No specific security updates in this release.

### Documentation
- No significant documentation changes in this release.

### Miscellaneous
- No miscellaneous changes in this release.

This release marks a significant transition in the project's development, focusing on major upgrades and architectural 
changes.

The move to virtual threads and upgrades in Java and the application framework reflect a commitment to leveraging the 
latest technologies for enhanced performance and scalability.

Although there are no new features or bug fixes in this snapshot, the foundational improvements set the stage for 
future development and innovation.



