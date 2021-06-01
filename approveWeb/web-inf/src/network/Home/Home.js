export function HomeData(data) {
  return new Promise((resolve, reject) => {
    const clientId = 'mqttjs_' + Math.random().toString(16).substr(2, 8)
    //设备ID，可以使用控制器传入，这里测试
    const deviceId = data;
    // const deviceId = "sn4108812020t0001";
    // const deviceId1 = "sn20210107t0001";
    // const deviceId1 = "sn4108812020t0001";
    //固定不变
    const options = {
      keepalive: 60,
      clean: true,
      reconnectPeriod: 1000,
      connectTimeout: 30 * 1000,
      protocolId: 'MQTT',
      protocolVersion: 4,
      clientId: clientId,
      username: 'hbcom',
      password: 'hbcom@12345'
    }

    //固定不变
    const client = mqtt.connect('ws://36.133.20.49:8000', options)

    //错误时执行
    client.on('error', function (err) {
      client.end()
    })

    //重新连接执行
    client.on('reconnect', () => {
    })

    //连接成功执行
    client.on('connect', function () {
      //订阅主题
      client.subscribe('/dev/pub/msg/' + deviceId.toLowerCase());
    })
    //服务器主动发送数据 topic为主题，message为消息
    client.on("message", (topic, message) => {
      var jsons = JSON.parse(message.toString());
      resolve(jsons)
    })
    //关闭使用
    client.on('close', () => {
    });
  })
}
