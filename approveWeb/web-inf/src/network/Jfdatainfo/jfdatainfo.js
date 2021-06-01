import {request} from '@/network/request.js';

export function initData(data) {
  return request({
    url: 'jfDatainfo/queryAllByLimit',
    method: 'post',
    data: data
  })
}
