import { Image } from 'react-native';

const getSize = (uri) => {
    return new Promise((resolve, reject) => {
        Image.getSize(uri, (width, height) => resolve({ width, height }), reject);
    })
}

export default { getSize };
