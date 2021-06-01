<template>
  <div class="checkmaps">
    <div id="allmap" style="width:1500px;height:800px"></div>
  </div>
</template>
<script>
import {map} from '../../src/network/map/map.js';
// import BMap from 'vue-baidu-map'
// Vue.use(BMap, {
//   ak: 'tnXg5saO2k00pAboF709MRHqERbMKiLm'
// })

export default {
  dataz() {
    return {
      mapId: 'BMap-' + parseInt(Date.now() + Math.random()),
      myMap: null
    }
  },
  methods: {
    initMap() {
      map('tnXg5saO2k00pAboF709MRHqERbMKiLm').then(() => {
        // 百度地图API功能
        var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
        var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
        var top_right_navigation = new BMap.NavigationControl({
          anchor: BMAP_ANCHOR_TOP_RIGHT,
          type: BMAP_NAVIGATION_CONTROL_SMALL
        }); //右上角，仅包含平移和缩放按钮

        this.myMap = new BMap.Map("allmap") // 创建Map实例
        this.myMap.centerAndZoom(new BMap.Point(114.049, 32.983), 11) // 初始化地图,设置中心点坐标和地图级别
        //添加地图类型控件
        this.myMap.addControl(
          new BMap.MapTypeControl({
            mapTypes: [BMAP_NORMAL_MAP, BMAP_HYBRID_MAP]
          })
        )
        this.myMap.addControl(top_left_control);
        this.myMap.addControl(top_left_navigation);
        this.myMap.addControl(top_right_navigation);


        var marker1 = new BMap.Marker(new BMap.Point (121.487899486,31.24916171))
        var marker2 = new BMap.Marker(new BMap.Point(114.04, 32.98));
        var marker3 = new BMap.Marker(new BMap.Point(116.404, 39.915));
        var marker4 = new BMap.Marker(new BMap.Point(116.395, 39.935));
        var marker5 = new BMap.Marker(new BMap.Point(116.415, 39.931));
        var marker6 = new BMap.Marker(new BMap.Point(100.092612914,23.8878061038));
        var marker7 = new BMap.Marker(new BMap.Point(100.229628399,26.8753510895));
        var marker8 = new BMap.Marker(new BMap.Point(99.1779956133,25.1204891962));
        var marker9 = new BMap.Marker(new BMap.Point(100.223674789,25.5968996394));
        var marker10 = new BMap.Marker(new BMap.Point(98.5894342874,24.441239663));
        var marker11 = new BMap.Marker(new BMap.Point(98.8599320425,25.8606769782));
        var marker12 = new BMap.Marker(new BMap.Point(104.246294318,23.3740868504));
        var marker13 = new BMap.Marker(new BMap.Point(102.714601139,25.0491531005));
        var marker14 = new BMap.Marker(new BMap.Point(103.725020656,27.3406329636));
        var marker15 = new BMap.Marker(new BMap.Point(100.98005773,22.7887777801));
        var marker16 = new BMap.Marker(new BMap.Point(103.782538888,25.5207581429));
        var marker5 = new BMap.Marker(new BMap.Point(101.529382239,25.0663556742));
        var marker5 = new BMap.Marker(new BMap.Point(102.545067892,24.3704471344));
        var marker5 = new BMap.Marker(new BMap.Point(103.384064757,23.3677175165));
        var marker5 = new BMap.Marker(new BMap.Point(100.803038275,22.0094330022));
        var marker5 = new BMap.Marker(new BMap.Point(99.7136815989,27.8310294612));//	迪庆藏族自治州
        var marker5 = new BMap.Marker(new BMap.Point(113.112846391,41.0223629468));//	乌兰察布市
        var marker5 = new BMap.Marker(new BMap.Point(106.831999097,39.6831770068));//	乌海市
        var marker5 = new BMap.Marker(new BMap.Point(122.048166514,46.0837570652));//	兴安盟
        var marker5 = new BMap.Marker(new BMap.Point(109.846238532,40.6471194257));//	包头市
        var marker5 = new BMap.Marker(new BMap.Point(119.760821794,49.2016360546));//	呼伦贝尔市
        var marker5 = new BMap.Marker(new BMap.Point(111.66035052,40.8283188731));//	呼和浩特市
        var marker5 = new BMap.Marker(new BMap.Point(107.42380672,40.7691799024));//	巴彦淖尔市
        var marker5 = new BMap.Marker(new BMap.Point(118.930761192,42.2971123203));//	赤峰市
        var marker5 = new BMap.Marker(new BMap.Point(122.260363263,43.633756073));//	通辽市
        var marker5 = new BMap.Marker(new BMap.Point( 109.993706251,39.8164895606));//	鄂尔多斯市
        var marker5 = new BMap.Marker(new BMap.Point(116.027339689,43.9397048423));	//锡林郭勒盟
        var marker5 = new BMap.Marker(new BMap.Point(105.695682871,38.8430752644));//	阿拉善盟
        var marker5 = new BMap.Marker(new BMap.Point(116.395645038,39.9299857781));//	北京市
        var marker5 = new BMap.Marker(new BMap.Point(119.337634104,26.0911937119));//	台中市
        var marker5 = new BMap.Marker(new BMap.Point(114.130474436,22.3748329286));//	台北市
        var marker5 = new BMap.Marker(new BMap.Point(121.360525873,38.9658447898));//	台南市
        var marker5 = new BMap.Marker(new BMap.Point(114.246701335,22.7288657203));//	嘉义市
        var marker5 = new BMap.Marker(new BMap.Point(111.590952812,21.9464822541));//	高雄市
        var marker5 = new BMap.Marker(new BMap.Point(126.564543989,43.8719883344));//	吉林市
        var marker5 = new BMap.Marker(new BMap.Point(124.391382074,43.1755247011));//	四平市
        var marker5 = new BMap.Marker(new BMap.Point(129.485901958,42.8964136037));//	延边朝鲜族自治州
        var marker5 = new BMap.Marker(new BMap.Point(124.832994532,45.1360489701));//	松原市
        var marker5 = new BMap.Marker(new BMap.Point(122.840776679,45.6210862752));//	白城市
        var marker5 = new BMap.Marker(new BMap.Point(126.435797675,41.945859397));//	白山市
        var marker5 = new BMap.Marker(new BMap.Point(125.133686052,42.9233026191));//	辽源市
        var marker5 = new BMap.Marker(new BMap.Point(125.942650139,41.7363971299));//	通化市
        var marker5 = new BMap.Marker(new BMap.Point(125.313642427,43.8983376071));//	长春市
        var marker5 = new BMap.Marker(new BMap.Point(103.760824239,29.6009576111));//	乐山市
        var marker5 = new BMap.Marker(new BMap.Point(105.073055992,29.5994615348));//	内江市
        var marker5 = new BMap.Marker(new BMap.Point(102.259590803,27.8923929037));//	凉山彝族自治州
        var marker5 = new BMap.Marker(new BMap.Point(106.105553984,30.8009651682));//	南充市
        var marker5 = new BMap.Marker(new BMap.Point(104.633019062,28.7696747963));//	宜宾市
        var marker5 = new BMap.Marker(new BMap.Point(106.757915842,31.8691891592));//	巴中市
        var marker5 = new BMap.Marker(new BMap.Point(105.81968694,32.4410401584));//	广元市
        var marker5 = new BMap.Marker(new BMap.Point(106.635720331,30.4639838879));//	广安市
        var marker5 = new BMap.Marker(new BMap.Point(104.402397818,31.1311396527));//	德阳市
        var marker5 = new BMap.Marker(new BMap.Point(104.067923463,30.6799428454));//	成都市
        var marker5 = new BMap.Marker(new BMap.Point(101.722423152,26.5875712571));//	攀枝花市
        var marker5 = new BMap.Marker(new BMap.Point(105.443970289,28.8959298039));//	泸州市
        var marker5 = new BMap.Marker(new BMap.Point(101.969232063,30.0551441144));//	甘孜藏族自治州
        var marker5 = new BMap.Marker(new BMap.Point(103.841429563,30.0611150799));//	眉山市
        var marker5 = new BMap.Marker(new BMap.Point(104.705518975,31.5047012581));//	绵阳市
        var marker5 = new BMap.Marker(new BMap.Point(104.776071339,29.3591568895));//	自贡市
        var marker5 = new BMap.Marker(new BMap.Point(104.635930302,30.132191434));//	资阳市
        var marker5 = new BMap.Marker(new BMap.Point(107.494973447,31.2141988589));//	达州市
        var marker5 = new BMap.Marker(new BMap.Point(105.564887792,30.5574913504));//	遂宁市
        var marker5 = new BMap.Marker(new BMap.Point(102.228564689,31.9057628583));//	阿坝藏族羌族自治州
        var marker5 = new BMap.Marker(new BMap.Point(103.009356466,29.9997163371));//	雅安市
        var marker5 = new BMap.Marker(new BMap.Point(117.210813092,39.1439299033));//	天津市
        var marker5 = new BMap.Marker(new BMap.Point(105.196754199,37.5211241916));//	中卫市
        var marker5 = new BMap.Marker(new BMap.Point(106.208254199,37.9935610029));//	吴忠市
        var marker5 = new BMap.Marker(new BMap.Point(106.285267996,36.0215234807));//	固原市
        var marker5 = new BMap.Marker(new BMap.Point(106.379337202,39.0202232836));//	石嘴山市
        var marker5 = new BMap.Marker(new BMap.Point(106.206478608,38.5026210119));//	银川市
        var marker5 = new BMap.Marker(new BMap.Point(115.787928245,33.8712105653));//	亳州市
        var marker5 = new BMap.Marker(new BMap.Point(116.505252683,31.7555583552));//	六安市
        var marker5 = new BMap.Marker(new BMap.Point(117.282699092,31.8669422607));//	合肥市
        var marker5 = new BMap.Marker(new BMap.Point(117.058738772,30.5378978174));//	安庆市
        var marker5 = new BMap.Marker(new BMap.Point(118.752096311,30.9516423543));//	宣城市
        var marker5 = new BMap.Marker(new BMap.Point(116.988692412,33.6367723858));//	宿州市
        var marker5 = new BMap.Marker(new BMap.Point(117.494476772,30.6600192482));//	池州市
        var marker5 = new BMap.Marker(new BMap.Point(116.791447429,33.9600233054));//	淮北市
        var marker5 = new BMap.Marker(new BMap.Point(117.018638863,32.6428118237));//	淮南市
        var marker5 = new BMap.Marker(new BMap.Point(118.324570351,32.3173505954));//	滁州市
        var marker5 = new BMap.Marker(new BMap.Point(118.384108423,31.3660197875));//	芜湖市
        var marker5 = new BMap.Marker(new BMap.Point(117.357079866,32.9294989067));//	蚌埠市
        var marker5 = new BMap.Marker(new BMap.Point(117.819428729,30.9409296947));//	铜陵市
        var marker5 = new BMap.Marker(new BMap.Point(115.820932259,32.9012113306));//	阜阳市
        var marker5 = new BMap.Marker(new BMap.Point(118.515881847,31.6885281589));//	马鞍山市
        var marker5 = new BMap.Marker(new BMap.Point(118.293569632,29.7344348562));//	黄山市
        var marker5 = new BMap.Marker(new BMap.Point(118.583926333,37.4871211553));//	东营市
        var marker5 = new BMap.Marker(new BMap.Point(118.340768237,35.0724090744));//	临沂市
        var marker5 = new BMap.Marker(new BMap.Point(122.093958366,37.5287870813));//	威海市
        var marker5 = new BMap.Marker(new BMap.Point(116.328161364,37.4608259263));//	德州市
        var marker5 = new BMap.Marker(new BMap.Point(119.507179943,35.4202251931));//	日照市
        var marker5 = new BMap.Marker(new BMap.Point(117.279305383,34.8078830784));//	枣庄市
        var marker5 = new BMap.Marker(new BMap.Point(117.089414917,36.1880777589));//	泰安市
        var marker5 = new BMap.Marker(new BMap.Point(117.024967066,36.6827847272));//	济南市
        var marker5 = new BMap.Marker(new BMap.Point(116.600797625,35.4021216643));//	济宁市
        var marker5 = new BMap.Marker(new BMap.Point(118.059134278,36.8046848542));//	淄博市
        var marker5 = new BMap.Marker(new BMap.Point(117.968292415,37.4053139418));//	滨州市
        var marker5 = new BMap.Marker(new BMap.Point(119.142633823,36.7161148731));//	潍坊市
        var marker5 = new BMap.Marker(new BMap.Point(121.30955503,37.5365615629));//	烟台市
        var marker5 = new BMap.Marker(new BMap.Point(115.986869139,36.4558285147));//	聊城市
        var marker5 = new BMap.Marker(new BMap.Point(117.684666912,36.2336541336));//	莱芜市
        var marker5 = new BMap.Marker(new BMap.Point(115.463359775,35.2624404961));//	菏泽市
        var marker5 = new BMap.Marker(new BMap.Point(120.384428184,36.1052149013));//	青岛市
        var marker5 = new BMap.Marker(new BMap.Point(111.538787596,36.0997454436));//	临汾市
        var marker5 = new BMap.Marker(new BMap.Point(111.143156602,37.527316097));//	吕梁市
        var marker5 = new BMap.Marker(new BMap.Point(113.290508673,40.1137444997));//	大同市
        var marker5 = new BMap.Marker(new BMap.Point(112.550863589,37.890277054));//	太原市
        var marker5 = new BMap.Marker(new BMap.Point(112.727938829,38.461030573));//	忻州市
        var marker5 = new BMap.Marker(new BMap.Point(112.7385144,37.6933615268));//	晋中市
        var marker5 = new BMap.Marker(new BMap.Point(112.867332758,35.4998344672));//	晋城市
        var marker5 = new BMap.Marker(new BMap.Point(112.479927727,39.3376719662));//	朔州市
        var marker5 = new BMap.Marker(new BMap.Point(111.006853653,35.0388594798));//	运城市
        var marker5 = new BMap.Marker(new BMap.Point(113.120292086,36.2016643857));//	长治市
        var marker5 = new BMap.Marker(new BMap.Point(113.569237602,37.8695294932));//	阳泉市
        var marker5 = new BMap.Marker(new BMap.Point(113.763433991,23.0430238154));//	东莞市
        var marker5 = new BMap.Marker(new BMap.Point(113.422060021,22.5451775145));//	中山市
        var marker5 = new BMap.Marker(new BMap.Point(112.050945959,22.9379756855));//	云浮市
        var marker5 = new BMap.Marker(new BMap.Point(113.134025635,23.0350948405));//	佛山市
        var marker5 = new BMap.Marker(new BMap.Point(113.307649675,23.1200491021));//	广州市
        var marker5 = new BMap.Marker(new BMap.Point(114.41065808,23.1135398524));//	惠州市
        var marker5 = new BMap.Marker(new BMap.Point(116.379500855,23.5479994669));//	揭阳市
        var marker5 = new BMap.Marker(new BMap.Point(116.126403098,24.304570606));//	梅州市
        var marker5 = new BMap.Marker(new BMap.Point(116.728650288,23.3839084533));//	汕头市
        var marker5 = new BMap.Marker(new BMap.Point(115.372924289,22.7787305002));//	汕尾市
        var marker5 = new BMap.Marker(new BMap.Point(113.078125341,22.5751167835));//	江门市
        var marker5 = new BMap.Marker(new BMap.Point(114.713721476,23.7572508505));//	河源市
        var marker5 = new BMap.Marker(new BMap.Point(114.025973657,22.5460535462));//	深圳市
        var marker5 = new BMap.Marker(new BMap.Point(113.040773349,23.6984685504));//	清远市
        var marker5 = new BMap.Marker(new BMap.Point(110.365067263,21.2574631038));//	湛江市
        var marker5 = new BMap.Marker(new BMap.Point(116.630075991,23.6618116765));//	潮州市
        var marker5 = new BMap.Marker(new BMap.Point(113.562447026,22.2569146461));//	珠海市
        var marker5 = new BMap.Marker(new BMap.Point(112.47965337,23.0786632829));//肇庆市
        var marker5 = new BMap.Marker(new BMap.Point(110.931245331,21.6682257188));//	茂名市
        var marker5 = new BMap.Marker(new BMap.Point(111.977009756,21.8715173045));//	阳江市
        var marker5 = new BMap.Marker(new BMap.Point(113.594461107,24.8029603119));//	韶关市
        var marker5 = new BMap.Marker(new BMap.Point(109.122627919,21.472718235));//北海市
        var marker5 = new BMap.Marker(new BMap.Point(108.297233556,22.8064929356));//	南宁市
        var marker5 = new BMap.Marker(new BMap.Point(107.357322038,22.4154552965));//	崇左市
        var marker5 = new BMap.Marker(new BMap.Point(109.231816505,23.7411659265));//	来宾市
        var marker5 = new BMap.Marker(new BMap.Point(109.42240181,24.3290533525));//	柳州市
        var marker5 = new BMap.Marker(new BMap.Point(110.260920147,25.262901246));//	桂林市
        var marker5 = new BMap.Marker(new BMap.Point(111.30547195,23.4853946367));//	梧州市
        var marker5 = new BMap.Marker(new BMap.Point(108.069947709,24.6995207829));//	河池市
        var marker5 = new BMap.Marker(new BMap.Point(110.151676316,22.6439736084));//	玉林市
        var marker5 = new BMap.Marker(new BMap.Point(106.631821404,23.9015123679));//	百色市
        var marker5 = new BMap.Marker(new BMap.Point(109.613707557,23.1033731644));//	贵港市
        var marker5 = new BMap.Marker(new BMap.Point(111.552594179,24.4110535471));//	贺州市
        var marker5 = new BMap.Marker(new BMap.Point(108.638798056,21.9733504653));//	钦州市
        var marker5 = new BMap.Marker(new BMap.Point(108.351791153,21.6173984705));//	防城港市
        var marker5 = new BMap.Marker(new BMap.Point(87.5649877411,43.8403803472));//	乌鲁木齐市
        var marker5 = new BMap.Marker(new BMap.Point(81.2978535304,43.9222480963));//	伊犁哈萨克自治州
        var marker5 = new BMap.Marker(new BMap.Point(76.1375644775,39.7503455778));//	克孜勒苏柯尔克孜自治州
        var marker5 = new BMap.Marker(new BMap.Point(84.8811801861,45.5943310667));//	克拉玛依市
        var marker5 = new BMap.Marker(new BMap.Point(82.0524362672,44.9136513743));//	博尔塔拉蒙古自治州
        var marker5 = new BMap.Marker(new BMap.Point(89.1815948657,42.9604700169));//	吐鲁番地区
        var marker5 = new BMap.Marker(new BMap.Point(79.9302386372,37.1167744927));//	和田地区
        var marker5 = new BMap.Marker(new BMap.Point(93.5283550928,42.8585963324));//	哈密地区
        var marker5 = new BMap.Marker(new BMap.Point(75.9929732675,39.4706271887));//	喀什地区
        var marker5 = new BMap.Marker(new BMap.Point(82.9748805837,46.7586836297));//	塔城地区
        var marker5 = new BMap.Marker(new BMap.Point(87.2960381257,44.0070578985));//	昌吉回族自治州
        var marker5 = new BMap.Marker(new BMap.Point(85.6148993383,42.1270009576));//	自治区直辖
        var marker5 = new BMap.Marker(new BMap.Point(80.2698461793,41.1717309015));//	阿克苏地区
        var marker5 = new BMap.Marker(new BMap.Point(88.1379154871,47.8397444862));//	阿勒泰地区
        var marker5 = new BMap.Marker(new BMap.Point(118.778074408,32.0572355018));//	南京市
        var marker5 = new BMap.Marker(new BMap.Point(120.873800951,32.0146645408));//南通市
        var marker5 = new BMap.Marker(new BMap.Point(118.296893379,33.9520497337));//	宿迁市
        var marker5 = new BMap.Marker(new BMap.Point(119.981861013,31.7713967447));//	常州市
        var marker5 = new BMap.Marker(new BMap.Point(117.188106623,34.2715534311));//	徐州市
        var marker5 = new BMap.Marker(new BMap.Point(119.427777551,32.4085052546));//	扬州市
        var marker5 = new BMap.Marker(new BMap.Point(120.305455901,31.5700374519));//	无锡市
        var marker5 = new BMap.Marker(new BMap.Point(119.919606016,32.4760532748));//	泰州市
        var marker5 = new BMap.Marker(new BMap.Point(119.030186365,33.6065127393));//	淮安市
        var marker5 = new BMap.Marker(new BMap.Point(120.148871818,33.3798618771));//	盐城市
        var marker5 = new BMap.Marker(new BMap.Point(120.619907115,31.317987368));//	苏州市
        var marker5 = new BMap.Marker(new BMap.Point(119.173872217,34.601548967));//	连云港市
        var marker5 = new BMap.Marker(new BMap.Point(119.455835405,32.2044094436));//	镇江市
        var marker5 = new BMap.Marker(new BMap.Point(117.955463877,28.4576225539));//	上饶市
        var marker5 = new BMap.Marker(new BMap.Point(115.999848022,29.7196395261));//	九江市
        var marker5 = new BMap.Marker(new BMap.Point(115.893527546,28.6895780001));//	南昌市
        var marker5 = new BMap.Marker(new BMap.Point(114.992038711,27.1138476502));//	吉安市
        var marker5 = new BMap.Marker(new BMap.Point(114.400038672,27.8111298958));//	宜春市
        var marker5 = new BMap.Marker(new BMap.Point(116.360918867,27.9545451703));//	抚州市
        var marker5 = new BMap.Marker(new BMap.Point(114.947117417,27.8223215586));//	新余市
        var marker5 = new BMap.Marker(new BMap.Point( 117.186522625,29.3035627684));//	景德镇市
        var marker5 = new BMap.Marker(new BMap.Point(113.859917033,27.639544223));//	萍乡市
        var marker5 = new BMap.Marker(new BMap.Point(114.935909079,25.8452955363));//	赣州市
        var marker5 = new BMap.Marker(new BMap.Point(117.035450186,28.2413095972));//	鹰潭市
        var marker5 = new BMap.Marker(new BMap.Point(115.494810169,38.886564548));//	保定市
        var marker5 = new BMap.Marker(new BMap.Point(118.183450598,39.6505309225));//	唐山市
        var marker5 = new BMap.Marker(new BMap.Point(116.703602223,39.5186106251));//	廊坊市
        var marker5 = new BMap.Marker(new BMap.Point(114.89378153,40.8111884911));//	张家口市
        var marker5 = new BMap.Marker(new BMap.Point(117.933822456,40.9925210525));//	承德市
        var marker5 = new BMap.Marker(new BMap.Point(116.863806476,38.2976153503));//	沧州市
        var marker5 = new BMap.Marker(new BMap.Point(114.522081844,38.0489583146));//	石家庄市
        var marker5 = new BMap.Marker(new BMap.Point(119.604367616,39.9454615659));//	秦皇岛市
        var marker5 = new BMap.Marker(new BMap.Point(115.686228653,37.7469290459));//	衡水市
        var marker5 = new BMap.Marker(new BMap.Point(114.520486813,37.0695311969));//	邢台市
        var marker5 = new BMap.Marker(new BMap.Point(114.482693932,36.6093079285));//	邯郸市
        // 111.181262093,34.7833199411	三门峡市
        // 114.085490993,32.1285823075	信阳市
        // 112.542841901,33.0114195691	南阳市
        // 114.654101942,33.6237408181	周口市
        // 115.641885688,34.4385886402	商丘市
        // 114.351806508,36.1102667222	安阳市
        // 113.300848978,33.7453014565	平顶山市
        // 114.351642118,34.8018541758	开封市
        // 113.912690161,35.3072575577	新乡市
        // 112.447524769,34.6573678177	洛阳市
        // 114.0460614,33.5762786885	漯河市
        // 115.026627441,35.7532978882	濮阳市
        // 113.211835885,35.234607555	焦作市
        // 113.486804058,34.157183768	省直辖
        // 113.83531246,34.0267395887	许昌市
        // 113.64964385,34.7566100641	郑州市
        // 114.049153547,32.9831581541	驻马店市
        // 114.297769838,35.7554258742	鹤壁市
        // 119.929575843,28.4562995521	丽水市
        // 121.440612936,28.6682832857	台州市
        // 120.760427699,30.7739922396	嘉兴市
        // 121.579005973,29.8852589659	宁波市
        // 120.219375416,30.2592444615	杭州市
        // 120.690634734,28.002837594	温州市
        // 120.137243163,30.8779251557	湖州市
        // 120.592467386,30.0023645805	绍兴市
        // 122.169872098,30.0360103026	舟山市
        // 118.875841652,28.9569104475	衢州市
        // 119.652575704,29.1028991054	金华市
        // 109.522771281,18.2577759149	三亚市
        // 112.350383075,16.840062894	三沙市
        // 110.330801848,20.022071277	海口市
        // 109.733755488,19.1805008013	省直辖
        // 110.801228917,32.6369943395	十堰市
        // 114.300060592,29.8806567577	咸宁市
        // 113.935734392,30.9279547842	孝感市
        // 111.310981092,30.732757818	宜昌市
        // 109.491923304,30.2858883166	恩施土家族苗族自治州
        // 114.316200103,30.5810841269	武汉市
        // 112.410562192,31.2093162501	省直辖
        // 112.241865807,30.332590523	荆州市
        // 112.217330299,31.0426112029	荆门市
        // 112.250092848,32.2291685915	襄阳市
        // 114.895594041,30.3844393228	鄂州市
        // 113.379358364,31.7178576082	随州市
        // 114.906618047,30.4461089379	黄冈市
        // 115.050683164,30.2161271277	黄石市
        // 111.996396357,27.7410733023	娄底市
        // 113.146195519,29.3780070755	岳阳市
        // 111.653718137,29.0121488552	常德市
        // 110.481620157,29.1248893532	张家界市
        // 109.986958796,27.5574829012	怀化市
        // 113.131695341,27.8274329277	株洲市
        // 111.614647686,26.4359716468	永州市
        // 112.935555633,27.835095053	湘潭市
        // 109.7457458,28.3179507937	湘西土家族苗族自治州
        // 112.366546645,28.5880877799	益阳市
        // 112.583818811,26.8981644154	衡阳市
        // 111.461525404,27.2368112449	邵阳市
        // 113.037704468,25.7822639757	郴州市
        // 112.979352788,28.2134782309	长沙市
        // 113.557519102,22.2041179884	无堂区划分区域
        // 113.566432335,22.1950041592	澳门半岛
        // 113.557519102,22.2041179884	澳门离岛
        // 103.215249178,35.5985143488	临夏回族自治州
        // 103.823305441,36.064225525	兰州市
        // 98.2816345853,39.8023973267	嘉峪关市
        // 105.736931623,34.5843194189	天水市
        // 104.626637601,35.5860562418	定西市
        // 106.688911157,35.55011019	平凉市
        // 107.644227087,35.7268007545	庆阳市
        // 100.459891869,38.939320297	张掖市
        // 102.640147343,37.9331721429	武威市
        // 102.917442486,34.9922111784	甘南藏族自治州
        // 104.171240904,36.5466817062	白银市
        // 98.5084145062,39.7414737682	酒泉市
        // 102.208126263,38.5160717995	金昌市
        // 104.934573406,33.3944799729	陇南市
        // 117.642193934,26.2708352794	三明市
        // 118.181882949,26.6436264742	南平市
        // 118.103886046,24.4892306125	厦门市
        // 119.54208215,26.6565274192	宁德市
        // 118.600362343,24.901652384	泉州市
        // 117.676204679,24.5170647798	漳州市
        // 119.330221107,26.0471254966	福州市
        // 119.077730964,25.4484501367	莆田市
        // 117.017996739,25.0786854335	龙岩市
        // 91.7506438744,29.2290269317	山南地区
        // 91.111890896,29.6625570621	拉萨市
        // 88.8914855677,29.2690232039	日喀则地区
        // 97.18558158,31.1405756319	昌都地区
        // 94.3499854582,29.6669406258	林芝地区
        // 92.0670183689,31.4806798301	那曲地区
        // 81.1076686895,30.4045565883	阿里地区
        // 104.85208676,26.5918660603	六盘水市
        // 105.928269966,26.2285945777	安顺市
        // 105.333323371,27.4085621313	毕节市
        // 106.709177096,26.6299067414	贵阳市
        // 106.931260316,27.6999613771	遵义市
        // 109.168558028,27.6749026906	铜仁市
        // 107.985352573,26.5839917665	黔东南苗族侗族自治州
        // 107.52320511,26.2645359974	黔南布依族苗族自治州
        // 104.900557798,25.0951480559	黔西南布依族苗族自治州
        // 124.338543115,40.1290228266	丹东市
        // 121.593477781,38.9487099383	大连市
        // 123.929819767,41.8773038296	抚顺市
        // 120.446162703,41.5718276679	朝阳市
        // 123.77806237,41.3258376266	本溪市
        // 123.432790922,41.8086447835	沈阳市
        // 122.07322781,41.141248023	盘锦市
        // 122.233391371,40.6686510665	营口市
        // 120.860757645,40.7430298813	葫芦岛市
        // 123.172451205,41.2733392656	辽阳市
        // 123.854849615,42.2997570121	铁岭市
        // 121.147748738,41.1308788759	锦州市
        // 121.660822129,42.0192501071	阜新市
        // 123.007763329,41.1187436822	鞍山市
        // 106.530635013,29.5446061089	重庆市
        // 108.707509278,34.345372996	咸阳市
        // 109.934208154,33.8739073951	商洛市
        // 109.038044563,32.70437045	安康市
        // 107.170645452,34.3640808097	宝鸡市
        // 109.500509757,36.6033203523	延安市
        // 109.745925744,38.2794392401	榆林市
        // 107.045477629,33.0815689782	汉中市
        // 109.483932697,34.5023579758	渭南市
        // 108.953098279,34.2777998978	西安市
        // 108.968067013,34.9083676964	铜川市
        // 100.223722769,34.4804845846	果洛藏族自治州
        // 102.085206987,36.5176101677	海东地区
        // 100.879802174,36.9606541011	海北藏族自治州
        // 100.624066094,36.2843638038	海南藏族自治州
        // 97.3426254153,37.3737990706	海西蒙古族藏族自治州
        // 97.0133161374,33.0062399097	玉树藏族自治州
        // 101.76792099,36.640738612	西宁市
        // 102.007600308,35.5228515517	黄南藏族自治州
        // 114.173291988,22.3072458588	九龙
        // 114.146701965,22.4274312754	新界
        // 114.183870524,22.2721034276	香港岛
        // 131.019048047,45.7750053686	七台河市
        // 128.910765978,47.7346850751	伊春市
        // 130.284734586,46.8137796047	佳木斯市
        // 131.17140174,46.6551020625	双鸭山市
        // 126.657716855,45.7732246332	哈尔滨市
        // 124.19610419,51.991788968	大兴安岭地区
        // 125.02183973,46.59670902	大庆市
        // 129.608035396,44.5885211528	牡丹江市
        // 126.989094572,46.646063927	绥化市
        // 130.941767273,45.3215398866	鸡西市
        // 130.292472051,47.3386659037	鹤岗市
        // 127.500830295,50.2506900907	黑河市
        // 123.987288942,47.3476998134	齐齐哈尔市


        this.myMap.addOverlay(marker1);
        this.myMap.addOverlay(marker2);
        this.myMap.addOverlay(marker3);
        this.myMap.addOverlay(marker4);
        this.myMap.addOverlay(marker5);
        this.myMap.addOverlay(marker6);
        this.myMap.addOverlay(marker7);
        this.myMap.addOverlay(marker8);
        this.myMap.addOverlay(marker1);
        this.myMap.addOverlay(marker2);
        this.myMap.addOverlay(marker3);
        this.myMap.addOverlay(marker4);
        this.myMap.addOverlay(marker5);
        this.myMap.addOverlay(marker6);
        this.myMap.addOverlay(marker7);
        this.myMap.addOverlay(marker8);
        this.myMap.addOverlay(marker1);
        this.myMap.addOverlay(marker2);
        this.myMap.addOverlay(marker3);
        this.myMap.addOverlay(marker4);
        this.myMap.addOverlay(marker5);
        this.myMap.addOverlay(marker6);
        this.myMap.addOverlay(marker7);
        this.myMap.addOverlay(marker8);
        this.myMap.addOverlay(marker1);
        this.myMap.addOverlay(marker2);
        this.myMap.addOverlay(marker3);
        this.myMap.addOverlay(marker4);
        this.myMap.addOverlay(marker5);
        this.myMap.addOverlay(marker6);
        this.myMap.addOverlay(marker7);
        this.myMap.addOverlay(marker8);
        this.myMap.addOverlay(marker1);
        this.myMap.addOverlay(marker2);
        this.myMap.addOverlay(marker3);
        this.myMap.addOverlay(marker4);
        this.myMap.addOverlay(marker5);
        this.myMap.addOverlay(marker6);
        this.myMap.addOverlay(marker7);
        this.myMap.addOverlay(marker8);
        this.myMap.addOverlay(marker1);
        this.myMap.addOverlay(marker2);
        this.myMap.addOverlay(marker3);
        this.myMap.addOverlay(marker4);
        this.myMap.addOverlay(marker5);
        this.myMap.addOverlay(marker6);
        this.myMap.addOverlay(marker7);
        this.myMap.addOverlay(marker8);


        var opts = {
          position: new BMap.Point(114.04, 32.98), // 指定文本标注所在的地理位置
          offset: new BMap.Size(30, -30) // 设置文本偏移量
        };
      // 创建文本标注对象
        var label = new BMap.Label('hello', opts);
      // 自定义文本标注样式
        label.setStyle({
          color: 'blue',
          borderRadius: '5px',
          borderColor: '#ccc',
          padding: '10px',
          fontSize: '16px',
          height: '30px',
          lineHeight: '30px',
          fontFamily: '微软雅黑'
        });
        this.myMap.addOverlay(label);
        this.myMap.setCurrentCity('驻马店市') // 设置地图显示的城市 此项是必须设置的
        this.myMap.enableScrollWheelZoom(true) //开启鼠标滚轮缩放
      })
        .catch(err => {
          console.log(err)
          console.log('地图加载失败')
        })
    },
  },
  mounted() {
    this.initMap()
  }
}
</script>
<style>
.checkmaps {
  margin-left: 80px;
  margin-top: 50px;
}
</style>

