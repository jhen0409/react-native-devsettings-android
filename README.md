# react-native-devsettings-android

The Android implementation for iOS [`NativeModules.DevSettings`](https://github.com/facebook/react-native/blob/6ad7e8281b37ee2ce6425363c0b17420d056807a/React/Modules/RCTDevSettings.mm#L231-L330) of React Native.

__*NOTE*__ The features will try PR to react-native, currently this package will help to used for most RN versions.

## Installation

```bash
$ npm install --save react-native-devsettings-android
$ react-native link react-native-devsettings-android
```

## Usage

These methods are only for debug mode, doesn't effect in release mode.

```js
import { NativeModules } from 'react-native'

// Methods
NativeModules.DevSettings.reload()
NativeModules.DevSettings.toggleElementInspector()
NativeModules.DevSettings.setIsDebuggingRemotely(bool)
NativeModules.DevSettings.setLiveReloadEnabled(bool)
NativeModules.DevSettings.setHotLoadingEnabled(bool)

// Extra method that haven't on iOS
NativeModules.DevSettings.show()
```

Doesn't support the following methods:

```js
NativeModules.DevSettings.setIsShakeToShowDevMenuEnabled(bool)
NativeModules.DevSettings.setProfilingEnabled(bool)
```

## Related projects

- [react-native-debugger](https://github.com/jhen0409/react-native-debugger) used `DevSettings` to provide [dev menu](https://github.com/jhen0409/react-native-debugger/blob/master/docs/shortcut-references.md#context-menu)

## License

[MIT](LICENSE.md)
