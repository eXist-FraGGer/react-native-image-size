# react-native-image-size

## Compatibility
| Dependency       | Version Requirement |
|------------------|---------------------|
| React Native     | >= 0.80.0           |
| Android          | API 21+ (SDK 34)    |
| iOS              | 12.0+ (JS-only)     |
| Gradle           | 9.0+                |

## How It Works
- **Android**: Uses a native module to read image dimensions and EXIF rotation.
- **iOS**: Wraps React Native's built-in [`Image.getSize`](https://reactnative.dev/docs/image#getsize) (no native code required).

## Installation (Local Development)
1. **Copy the library** into your project (e.g., `libraries/react-native-image-size`).
2. **Add to `package.json`:**
   ```json
   "dependencies": {
     "react-native-image-size": "file:./libraries/react-native-image-size"
   }
   ```
3. **Install dependencies:**
   ```bash
   yarn install
   ```
4. **Rebuild your app:**
   ```bash
   npx react-native run-android  # or run-ios
   ```

## Usage
```javascript
import { getSize } from 'react-native-image-size';

// Works on both platforms (rotation is 0 on iOS)
const { width, height, rotation } = await getSize('https://example.com/image.jpg');
console.log(width, height, rotation);
```

## Troubleshooting
### Android
- **"Module not found"**: Verify the path in `package.json`.
- **Linking errors**: Run `npx react-native clean` and rebuild.

### iOS
- Ensure your project uses React Native 0.80+ (no native linking required).
