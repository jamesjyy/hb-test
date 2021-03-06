export function map(ak) {
  return new Promise(function (resolve, reject) {
    if (typeof BMap !== 'undefined') {
      resolve(BMap)
      return true
    }
    window.onBMapCallback = function () {
      resolve(BMap)
    }
    let script = document.createElement('script')
    script.type = 'text/javascript'
    script.src =
      'http://api.map.baidu.com/api?v=2.0&ak=' + 'tnXg5saO2k00pAboF709MRHqERbMKiLm' + '&callback=onBMapCallback'
    script.onerror = reject
    document.head.appendChild(script)
  })
}
