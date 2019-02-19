import { NativeModules } from 'react-native';


const { DetectSoftNav } = NativeModules;

export default { getSize: DetectSoftNav.getSize };
