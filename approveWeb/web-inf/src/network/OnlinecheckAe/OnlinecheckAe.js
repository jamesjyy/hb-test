import {request} from '@/network/request.js';

export function initData() {
  return request({
    method: "POST",
    cache: false,
    url: "http://192.168.100.3:8001",
    // headers: {
    //   'Authorization': 'Basic aGJjb206aGJjb21AMTIzNDU='
    // },
    data: "select * from db_env.tb_sn20210107t0001;",
  })
}
