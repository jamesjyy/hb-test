import {request} from '@/network/request.js';

export function initData(data) {
  return request({
    url: 'dyData/analyseDataAll',
    method: 'post',
    data: data
  })
}
