# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Added
- `setEndButtonVisible` and `setBackButtonVisible` methods in `StepViewModel.Builder` for toggling button visibility (issue #104)
- New stepper type `none` which shows no progress indicator for the steps (issue #154)

### Changed
- Updated Android Support Library version to `25.4.0` to support vector animations without a pre-Lollipop fallback (issue #154)
- Changed `setNextButtonLabel` methods in `StepViewModel.Builder` to `setEndButtonLabel` so that it works for both Next and Complete buttons (issue #107)

[Unreleased]: https://github.com/stepstone-tech/android-material-stepper/compare/v3.3.0...develop
