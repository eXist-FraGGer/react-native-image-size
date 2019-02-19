import { NativeModules } from 'react-native';


const { RNImageSize } = NativeModules;

console.info('!!!', { NativeModules, RNImageSize });

export default { getSize: RNImageSize.getSize };
