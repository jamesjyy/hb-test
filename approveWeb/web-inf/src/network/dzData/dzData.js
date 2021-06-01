import {request} from '@/network/request.js';

export function initData(data) {
  return request({
    url: 'dzData/views',
    method: 'post',
    data: data
  })
}
