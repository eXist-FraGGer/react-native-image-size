## react-native-image-size (iOS + Android)

[![NPM version](https://badge.fury.io/js/react-native-image-size.svg)](http://badge.fury.io/js/react-native-image-size)

Android 4.0 (API level 14) introduced the ability to get original image size.

iOS uses Image.getSize https://facebook.github.io/react-native/docs/image#getsize


### Installation

Download via NPM

```shell
npm i -S react-native-image-size
```

Download via Yarn

```shell
yarn add react-native-image-size
```

Afterward make sure to rebuild app, not just refresh bundler.

## Linking (for React Native <= 0.59 only, React Native >= 0.60 skip this as auto-linking should work)

**-- Automaticaly --**

Link, either via `react-native link` or manually

```shell
react-native link react-native-image-size
```

**-- Manually --**

#### android/settings.gradle
```diff
+include ':react-native-image-size'
+project(':react-native-image-size').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-image-size/android')
```
#### android/app/build.gradle
```diff
dependencies {
  ...
+  implementation project(':react-native-image-size')
  ...
}
```
#### android/app/src/main/java/.../MainApplication.java
```diff
+import com.existfragger.rnimagesize.RNImageSizePackage;
...
@Override
protected List<ReactPackage> getPackages() {
  return Arrays.<~>asList(
-    new MainReactPackage()
+    new MainReactPackage(),
+    new RNImageSizePackage()
  );
}
```

### How to use

```js
import ImageSize from 'react-native-image-size'
...
ImageSize.getSize(uri).then(size => {
    // size.height
    // size.width
})
```

You can also use async/await, if you would prefer.

```js
import ImageSize from 'react-native-image-size'
...
foo = async () => {
  const { width, height } = await ImageSize.getSize(uri);
  // do stuff with width and height
}
```
