import {request} from '@/network/request.js';

export function initData(data) {
  return request({
    url: 'dyData/views',
    method: 'get',
    data: data
  })
}
