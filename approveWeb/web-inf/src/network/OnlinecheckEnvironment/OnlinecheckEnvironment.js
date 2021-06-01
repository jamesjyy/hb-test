import {request} from '@/network/request.js';

export function initData(data) {
  return request({
    url: 'checkonline/dbenvironment',
    method: 'post',
    data: data
  })
}
