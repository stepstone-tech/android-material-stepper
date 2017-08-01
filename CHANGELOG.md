# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [4.0.0]
### Added
- `setEndButtonVisible` and `setBackButtonVisible` methods in `StepViewModel.Builder` for toggling button visibility (issue #104)
- New stepper type `none` which shows no progress indicator for the steps (issue #154)
- New stepper feedback type `disabled_content_interaction` which intercepts touch events on the steps' content and ignores them during operation.
- New stepper feedback type `content_overlay` which shows a dimmed overlay over the content.
- An option to specify the background drawable for `content_overlay` stepper feedback type via `ms_stepperFeedback_contentOverlayBackground`.
- An option to specify the fade out alpha for `content_fade` stepper feedback type via `ms_stepperFeedback_contentFadeAlpha` attribute.

### Changed
- **Breaking change:** Updated Android Support Library version to `25.4.0` to support vector animations without a pre-Lollipop fallback (issue #154)
- **Breaking change:** Changed `setNextButtonLabel` methods in `StepViewModel.Builder` to `setEndButtonLabel` so that it works for both Next and Complete buttons (issue #107)
- **Breaking change:** Split `content` stepper feedback type into `content_progress` and `content_fade`.

[4.0.0]: https://github.com/stepstone-tech/android-material-stepper/compare/v3.3.0...4.0.0
