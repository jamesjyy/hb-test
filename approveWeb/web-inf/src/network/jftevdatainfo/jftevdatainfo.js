import {request} from '@/network/request.js';

export function initData(data) {
  return request({
    url: 'jfTevdatainfo/queryAllByLimit',
    method: 'post',
    data: data
  })
}
