# react-native-k4l-video-trimmer

Whatsapp like trimmer for react native android

## Installation

```sh
npm install react-native-k4l-video-trimmer
```

## Usage

```js
import { navigateToTrimmer } from "react-native-k4l-video-trimmer";

// ...

navigateToTrimmer(this.state.video.uri, this.state.duration).then(trimmedUrl => {
        if (trimmedUrl == null) {
          return;
        }
        this.props.onTrim(trimmedUrl);
      }).catch(error => console.log('trimmed url error', error))
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
