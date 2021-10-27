import CameraRoll, {
  PhotoIdentifier,
} from '@react-native-community/cameraroll';
import * as React from 'react';

import { StyleSheet, View, Text, Button, ImageBackground } from 'react-native';
import { multiply, navigateToTrimmer } from 'react-native-k4l-video-trimmer';
import { requestMultiple, PERMISSIONS } from 'react-native-permissions';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();
  const [photos, setPhotos] = React.useState<
    Array<PhotoIdentifier> | undefined
  >();

  React.useEffect(() => {
    multiply(3, 7).then(setResult);
    requestMultiple([
      PERMISSIONS.ANDROID.READ_EXTERNAL_STORAGE,
      PERMISSIONS.ANDROID.WRITE_EXTERNAL_STORAGE,
      PERMISSIONS.ANDROID.CAMERA,
    ]).then(() => {
      CameraRoll.getPhotos({
        first: 50,
        assetType: 'Videos',
      }).then((r) => {
        console.log('camera roll', JSON.stringify(r));
        setPhotos(r.edges);
      });
    });
  }, []);

  const renderPhotos = () => {
    photos?.map((item) => {
      return (
        <View>
          <ImageBackground
            source={{ uri: item.node.image.uri }}
            style={styles.image}
            resizeMode="cover"
          />
        </View>
      );
    });
  };

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
      {renderPhotos()}
      <Button
        onPress={() =>
          navigateToTrimmer(
            'file:///storage/emulated/0/Download/intro.mp4',
            '5'
          ).then((r) => console.log(r))
        }
        title={'Click me'}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
  image: {
    alignItems: 'center',
    height: 100,
    justifyContent: 'center',
    width: 100,
  },
});
