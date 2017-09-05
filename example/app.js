import React, { Component } from 'react'
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  Button,
  NativeModules,
} from 'react-native'

const { DevSettings, DevMenu } = NativeModules

export default class example extends Component {
  renderDivider() {
    return <View style={{ margin: 4 }} />
  }
  render() {
    return (
      <View style={styles.container}>
        <Button title="Reload JS" onPress={() => DevSettings.reload()} />
        {this.renderDivider()}
        <Button
          title="Toggle element inspector"
          onPress={() => DevSettings.toggleElementInspector()}
        />
        {this.renderDivider()}
        <Button
          title="Turn on debugging remotely mode"
          onPress={() => DevSettings.setIsDebuggingRemotely(true)}
        />
        {this.renderDivider()}
        <Button
          title="Turn off debugging remotely mode"
          color="red"
          onPress={() => DevSettings.setIsDebuggingRemotely(false)}
        />
        {this.renderDivider()}
        <Button
          title="Turn on live reload"
          onPress={() => DevSettings.setLiveReloadEnabled(true)}
        />
        {this.renderDivider()}
        <Button
          title="Turn off live reload"
          color="red"
          onPress={() => DevSettings.setLiveReloadEnabled(false)}
        />
        {this.renderDivider()}
        <Button
          title="Turn on hot reloading"
          onPress={() => DevSettings.setHotLoadingEnabled(true)}
        />
        {this.renderDivider()}
        <Button
          title="Turn off hot reloading"
          color="red"
          onPress={() => DevSettings.setHotLoadingEnabled(false)}
        />
        {this.renderDivider()}
        <Text>Extra method (iOS: from DevMenu)</Text>
        {this.renderDivider()}
        <Button
          title="Show Developer Menu"
          onPress={() =>
            DevSettings.show ? DevSettings.show() : DevMenu.show()}
        />
      </View>
    )
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
})

AppRegistry.registerComponent('example', () => example)
