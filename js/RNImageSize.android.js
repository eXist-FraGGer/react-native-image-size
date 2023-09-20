import { NativeModules } from 'react-native';

const { RNImageSize } = NativeModules;

export default { getSize: RNImageSize.getSize };
