declare module 'react-native-image-size' {
    interface ImageSizeResult {
        width: number;
        height: number;
        rotation: number;
    }

    export function getSize(uri: string): Promise<ImageSizeResult>;
}
