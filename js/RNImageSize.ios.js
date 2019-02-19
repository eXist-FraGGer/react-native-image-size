import { Image } from 'react-native';


const getSize = () => {
    return new Promise((resolve, reject) => {
        Image.getSize(image.uri, (width, height) => resolve({ width, height }), reject);
    })
}

export default { getSize };
