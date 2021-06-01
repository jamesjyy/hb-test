import {request} from '@/network/request.js';

export function initData(data) {
  return request({
    url: 'user/querycompany',
    method: 'post',
    data: data
  })
}
