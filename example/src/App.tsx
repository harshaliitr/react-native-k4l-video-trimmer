import CameraRoll, { PhotoIdentifier, PhotoIdentifiersPage } from '@react-native-community/cameraroll';
import * as React from 'react';

import { StyleSheet, View, Text, Button, ImageBackground } from 'react-native';
import { multiply, navigateToTrimmer } from 'react-native-k4l-video-trimmer';
import { requestMultiple, PERMISSIONS } from 'react-native-permissions';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();
  const [photos, setPhotos] = React.useState<Array<PhotoIdentifier> | undefined>();

  React.useEffect(() => {
    multiply(3, 7).then(setResult);
    requestMultiple([PERMISSIONS.ANDROID.READ_EXTERNAL_STORAGE, PERMISSIONS.ANDROID.WRITE_EXTERNAL_STORAGE, PERMISSIONS.ANDROID.CAMERA]).then(() => {
      CameraRoll.getPhotos({
        first: 50,
        assetType: 'Videos',
      }).then((r) => {
        console.log('camera roll', JSON.stringify(r))
        setPhotos(r.edges);
      });
    })
  }, []);

  const renderPhotos = () => {
    photos?.map(item => {
      return (
        <View style={{ backgroundColor: 'red' }}>
          <ImageBackground
            source={{ uri: item.node.image.uri }}
            style={{
              alignItems: 'center',
              height: 100,
              justifyContent: 'center',
              width: 100,
            }}
            resizeMode="cover">
            
          </ImageBackground>
        </View>
      )
    })
  }

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
      {renderPhotos()}
      <Button onPress={() => navigateToTrimmer("file:///storage/emulated/0/DCIM/Camera/VID_20211018_172837.mp4")} title={'Click me'}></Button>
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
});
