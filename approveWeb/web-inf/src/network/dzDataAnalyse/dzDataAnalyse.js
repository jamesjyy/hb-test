import {request} from '@/network/request.js';

export function initData(data) {
  return request({
    url: 'dzData/analyseDataAll',
    method: 'post',
    data: data
  })
}
