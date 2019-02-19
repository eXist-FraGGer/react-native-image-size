## react-native-image-size

Android 4.0 (API level 14) introduced the ability to get orifinal image size.
iOS uses [Image.getSize] (https://facebook.github.io/react-native/docs/image#getsize)


### Installation

Download via NPM

```shell
npm i -S github:exist-fragger/react-native-image-size
```

Link, either via `react-native link` or manually

```shell
react-native link react-native-image-size
```

**-- OR --**

```diff
// android/settings.gradle
// add the following at the end of the file
+include ':react-native-image-size'
+project(':react-native-image-size').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-image-size/android')

// android/app/build.gradle
dependencies {
  ...
+  implementation project(':react-native-image-size')
}

// android/app/src/main/java/.../MainApplication.java
import com.existfragger.rnimagesize.RNImageSizePackage;
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
  // do stuff with v
}
```
