import React, { useState } from "react";

import { StyleSheet, View, Text, Image } from "react-native";
import ImageSize from "react-native-image-size";

const exampleImage = "./example.jpg";

export default function App() {
  const [size, setSize] = useState<
    { width: number; height: number; orientation?: number } | undefined
  >();

  React.useEffect(() => {
    const updateSize = async () => {
      const uri = Image.resolveAssetSource(require(exampleImage)).uri;
      const size = await ImageSize.getSize(uri);
      setSize(size);
    };
    updateSize();
  }, []);

  return (
    <View style={styles.container}>
      {size ? (
        <>
          <Image
            source={require(exampleImage)}
            style={{ width: size.width, height: size.height }}
          />
          <Text>
            Width: {size.width}, Height: {size.height}
          </Text>
        </>
      ) : null}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
});
