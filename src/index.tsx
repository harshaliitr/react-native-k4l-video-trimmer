import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-k4l-video-trimmer' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const K4lVideoTrimmer = NativeModules.K4lVideoTrimmer
  ? NativeModules.K4lVideoTrimmer
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function multiply(a: number, b: number): Promise<number> {
  return K4lVideoTrimmer.multiply(a, b);
}

export function navigateToTrimmer(uri: string): Promise<string> {
  // try {
  return K4lVideoTrimmer.navigateToTrimmer(uri);
  // } catch (error) {
  //   console.log(JSON.stringify(error));
  // }
}
