export function drawDiagonal(data,loopnameb,number){
        var x;
        var y;
        var z;
        //取得context对象
            var canvas=document.getElementById('diagonal');
            var context=canvas.getContext('2d');
            context.strokeStyle = "#ccc"; // 设置线的颜色
            context.fillStyle="#ccc";
            context.fill();
            //用绝对坐标创建一条路径
            context.beginPath();//开始路径绘制
            context.lineWidth=5;
            //塔尖四条
            context.moveTo(675,50);//起
            context.lineTo(740,130);//止
            context.moveTo(675,50);//起
            context.lineTo(610,135);//止
            context.moveTo(675,50);//起
            context.lineTo(665,115);//止
            context.moveTo(675,50);//起
            context.lineTo(695,135);//止

            //封顶四条
            context.moveTo(695,135);//起
            context.lineTo(740,130);//止
            context.lineTo(665,115);//止
            context.lineTo(610,135);//止
            context.lineTo(695,135);//止
            //第一根线 前面的线
            context.moveTo(695,135);//起
            context.lineTo(695,900);//止
            //
            //第二根线 后面的线
            context.moveTo(665,115);//起
            context.lineTo(600,800);//止
            //第三根线 左边的线
            context.moveTo(610,135);//起
            context.lineTo(390,860);//止
            //第四根线 右边的线
            context.moveTo(740,130);//起
            context.lineTo(940,860);//止

            //第2个四条
            context.moveTo(695,245);//起
            context.lineTo(765,220);//止
            context.lineTo(655,200);//止
            context.lineTo(580,230);//止
            context.lineTo(695,245);//止
            //斜线
            context.moveTo(560,305);//起
            context.lineTo(640,235);//止
            context.moveTo(640,235);//起
            context.lineTo(695,315);//止
            context.moveTo(695,315);//起
            context.lineTo(730,230);//止
            context.moveTo(730,230);//起
            context.lineTo(780,280);//止

            //第3个四条
            context.moveTo(695,315);//起
            context.lineTo(780,280);//止
            context.lineTo(650,265);//止
            context.lineTo(560,305);//止
            context.lineTo(695,315);//止

            //斜线
            context.moveTo(545,360);//起
            context.lineTo(640,310);//止
            context.moveTo(640,310);//起
            context.lineTo(695,375);//止
            context.moveTo(695,375);//起
            context.lineTo(730,300);//止
            context.moveTo(730,300);//起
            context.lineTo(795,340);//止

            //第4个四条
            context.moveTo(695,375);//起
            context.lineTo(795,340);//止
            context.lineTo(650,330);//止
            context.lineTo(545,360);//止
            context.lineTo(695,375);//止

            //斜线
            context.moveTo(520,450);//起
            context.lineTo(630,370);//止
            context.moveTo(630,370);//起
            context.lineTo(695,465);//止
            context.moveTo(695,465);//起
            context.lineTo(750,355);//止
            context.moveTo(750,355);//起
            context.lineTo(820,425);//止
            //第5个四条
            context.moveTo(695,465);//起
            context.lineTo(820,425);//止
            context.lineTo(640,410);//止
            context.lineTo(520,450);//止
            context.lineTo(695,465);//止
            //斜线
            context.moveTo(485,550);//起
            context.lineTo(620,460);//止
            context.moveTo(620,460);//起
            context.lineTo(695,565);//止
            context.moveTo(695,565);//起
            context.lineTo(760,445);//止
            context.moveTo(760,445);//起
            context.lineTo(850,525);//止
            //第6个四条
            context.moveTo(695,565);//起
            context.lineTo(850,525);//止
            context.lineTo(630,500);//止
            context.lineTo(485,550);//止
            context.lineTo(695,565);//止
            //斜线
            context.moveTo(460,635);//起
            context.lineTo(620,560);//止
            context.moveTo(620,560);//起
            context.lineTo(695,645);//止
            context.moveTo(695,645);//起
            context.lineTo(760,550);//止
            context.moveTo(760,550);//起
            context.lineTo(870,600);//止
            //第7个四条
            context.moveTo(695,645);//起
            context.lineTo(870,600);//止
            context.lineTo(625,580);//止
            context.lineTo(460,635);//止
            context.lineTo(695,645);//止
            //斜线
            context.moveTo(430,720);//起
            context.lineTo(610,640);//止
            context.moveTo(610,640);//起
            context.lineTo(695,740);//止
            context.moveTo(695,740);//起
            context.lineTo(760,630);//止
            context.moveTo(760,630);//起
            context.lineTo(890,690);//止
            // 第8个四条
            context.moveTo(695,740);//起
            context.lineTo(890,690);//止
            context.lineTo(610,670);//止
            context.lineTo(430,720);//止
            context.lineTo(695,740);//止

            //底部斜线
            context.moveTo(695,900);//起
            context.lineTo(790,720);//止
            context.lineTo(940,860);//止
            context.lineTo(720,680);//止
            context.lineTo(600,800);//止
            context.lineTo(520,695);//止
            context.lineTo(390,860);//止
            context.lineTo(575,730);//止
            context.lineTo(695,900);//止

            //底角矩形
            context.fillRect(650, 800, 20, 20);//前
            context.fillRect(580, 800, 20, 20);//后
            context.fillRect(385, 855, 20, 20);//左
            context.fillRect(920, 855, 20, 20);//左
            //将这条线绘制到canvas上
            context.strokeStyle = "#ccc"; // 设置线的颜色
            context.fillStyle="#6bafed";
            context.fill();
            context.stroke();
        //判断 相别有几种 3 6
              let  resganta = data
            if (resganta.length>0 && resganta.length<=3) {
                x=0;
                y=0;
                for (var i = 0; i < resganta.length; i++) {
                    resganta[i]=resganta[i].split("-");//将字符串拆成串数数组
                    x=0;
                    for (var j = 0; j < resganta[i].length; j++) {
                        resganta[i][j]=resganta[i][j].split(",");//串数
                        y=10;
                        for (var k = 0; k < resganta[i][j].length; k++) {//遍历每串的个数
                            if (i==0) {//A相上
                                context.beginPath();//开始路径绘制
                                context.lineWidth=1;
                                context.strokeStyle = "#333"; // 设置线的颜色
                                context.moveTo(500-x,135);//起
                                context.lineTo(500-x,135+(resganta[i][j].length*10));//止
                                context.stroke();
                                if (resganta[i][j][k]<=500) {
                                    EllipseOne(context,500-x,135+y,30,3);
                                    y+=10;
                                    context.fillStyle = "#d62323"; //故障绝缘子颜色
                                    context.fill();//填充
                                }else{
                                    EllipseOne(context,500-x,135+y,30,3);
                                    y+=10;
                                }
                                context.font="bold 20px Arial";
                                context.fillStyle="#333";
                                context.fillText("A"+(j+1),500-x,110);
                                //塔臂
                                context.beginPath();//开始路径绘制
                                context.lineWidth=3;
                                // A
                                context.moveTo(610,135);//起
                                context.lineTo(610-(resganta[i].length*100)-20,135);//止
                                context.moveTo(630,105);
                                context.lineTo(610-(resganta[i].length*100)-20,135);//止
                                context.moveTo(590,125);
                                context.lineTo(610-(resganta[i].length*100)-20,135);//止
                                context.moveTo(610,135);
                                context.lineTo(630,105);//止
                                context.moveTo(630,105);
                                context.lineTo(590,125);//止
                                context.moveTo(590,125);
                                context.lineTo(610,135);//止
                                context.strokeStyle = "#ccc"; // 设置线的颜色
                                context.stroke();
                                //A导线
                                if (j+1 == resganta[i].length) {
                                    context.beginPath();//开始路径绘制
                                    context.lineWidth=3;
                                    context.moveTo(600-resganta[i].length*100,135+resganta[i][j].length*10);
                                    context.lineTo(100,235+(resganta[i][j].length*10));//止0
                                    context.strokeStyle = "#333"; // 设置线的颜色
                                    context.stroke();
                                };

                            }else if(i==1){//B相上
                                context.beginPath();//开始路径绘制
                                context.lineWidth=1;
                                context.strokeStyle = "#333"; // 设置线的颜色
                                context.moveTo(440-x,360);//起
                                context.lineTo(440-x,360+(resganta[i][j].length*10));//止
                                context.stroke();
                                if (resganta[i][j][k]<=500) {
                                    EllipseOne(context,440-x,360+y,30,3);
                                    y+=10;
                                    context.fillStyle = "#d62323"; //故障绝缘子颜色
                                    context.fill();//填充
                                }else{
                                    EllipseOne(context,440-x,360+y,30,3);
                                    y+=10;
                                }
                                context.font="bold 20px Arial";
                                context.fillStyle="#333";
                                context.fillText("B"+(j+1),440-x,335);
                                //塔臂
                                context.beginPath();//开始路径绘制
                                context.lineWidth=3;
                                // B
                                context.moveTo(540,360);//起
                                context.lineTo(550-(resganta[i].length*100)-20,360);//止
                                context.moveTo(550,330);
                                context.lineTo(550-(resganta[i].length*100)-20,360);//止
                                context.moveTo(500,350);
                                context.lineTo(550-(resganta[i].length*100)-20,360);//止
                                context.moveTo(550,330);
                                context.lineTo(540,360);//止
                                context.moveTo(550,330);
                                context.lineTo(500,350);//止
                                context.moveTo(500,350);
                                context.lineTo(540,360);//止
                                context.strokeStyle = "#ccc"; // 设置线的颜色
                                context.stroke();
                                //B导线
                                if (j+1 == resganta[i].length) {
                                    context.beginPath();//开始路径绘制
                                    context.lineWidth=3;
                                    context.moveTo(530-(resganta[i].length*100),360+(resganta[i][j].length*10));
                                    context.lineTo(100,460+(resganta[i][j].length*10));//止
                                    context.strokeStyle = "#333"; // 设置线的颜色
                                    context.stroke();
                                };

                            }else if(i==2){//C相上
                                context.beginPath();//开始路径绘制
                                context.lineWidth=1;
                                context.strokeStyle = "#333"; // 设置线的颜色
                                context.moveTo(890+x,340);//起
                                context.lineTo(890+x,340+(resganta[i][j].length*10));//止
                                context.stroke();
                                if (resganta[i][j][k]<=500) {
                                    EllipseOne(context,890+x,340+y,30,3);
                                    y+=10;
                                    context.fillStyle = "#d62323"; //故障绝缘子颜色
                                    context.fill();//填充
                                }else{
                                    EllipseOne(context,890+x,340+y,30,3);
                                    y+=10;
                                }
                                context.font="bold 20px Arial";
                                context.fillStyle="#333";
                                context.fillText("C"+(j+1),890+x,315);
                                //塔臂
                                context.beginPath();//开始路径绘制
                                context.lineWidth=3;
                                context.moveTo(795,340);//起
                                context.lineTo(795+(resganta[i].length*100)+20,340);//止
                                context.moveTo(815,330);
                                context.lineTo(795+(resganta[i].length*100)+20,340);//止
                                context.moveTo(790,310);
                                context.lineTo(795+(resganta[i].length*100)+20,340);//止
                                context.moveTo(795,340);
                                context.lineTo(815,330);//止
                                context.moveTo(815,330);
                                context.lineTo(790,310);//止
                                context.moveTo(790,310);
                                context.lineTo(795,340);//止
                                context.strokeStyle = "#ccc"; // 设置线的颜色
                                context.stroke();
                                //C导线
                                if (j+1 == resganta[i].length) {
                                    context.beginPath();//开始路径绘制
                                    context.lineWidth=3;
                                    context.moveTo(795+(resganta[i].length*100),340+(resganta[i][j].length*10));
                                    context.lineTo(795+(resganta[i].length*100)-100,390+(resganta[i][j].length*10));//止
                                    context.moveTo(795+(resganta[i].length*100),340+(resganta[i][j].length*10));
                                    context.lineTo(795+(resganta[i].length*100)+100,290+(resganta[i][j].length*10));//止
                                    context.strokeStyle = "#333"; // 设置线的颜色
                                    context.stroke();
                                }
                            };
                        };
                         x+=100;
                    };
                };
            }else if (resganta.length>3 && resganta.length<=6) {
                y=0;
                x=0;
                for (var i = 0; i < resganta.length; i++) {
                    resganta[i]=resganta[i].split("-");//将字符串拆成串数数组
                    x=0;
                    for (var j = 0; j < resganta[i].length; j++) {
                        resganta[i][j]=resganta[i][j].split(",");//每串的个数
                        y=10;
                        for (var k = 0; k < resganta[i][j].length; k++) {//遍历每串的个数
                            if (i==0) {//A相上
                                context.beginPath();//开始路径绘制
                                context.lineWidth=1;
                                context.strokeStyle = "#333"; // 设置线的颜色
                                context.moveTo(500-x,135);//起
                                context.lineTo(500-x,135+(resganta[i][j].length*10));//止
                                context.stroke();
                                if (resganta[i][j][k]<=500) {
                                    EllipseOne(context,500-x,135+y,30,3);
                                    y+=10;
                                    context.fillStyle = "#d62323"; //故障绝缘子颜色
                                    context.fill();//填充
                                }else{
                                    EllipseOne(context,500-x,135+y,30,3);
                                    y+=10;
                                }
                                context.font="bold 20px Arial";
                                context.fillStyle="#333";
                                context.fillText("A"+(j+1),500-x,110);
                                //塔臂
                                context.beginPath();//开始路径绘制
                                context.lineWidth=3;
                                // A
                                context.moveTo(610,135);//起
                                context.lineTo(610-(resganta[i].length*100)-20,135);//止
                                context.moveTo(630,105);
                                context.lineTo(610-(resganta[i].length*100)-20,135);//止
                                context.moveTo(590,125);
                                context.lineTo(610-(resganta[i].length*100)-20,135);//止
                                context.moveTo(610,135);
                                context.lineTo(630,105);//止
                                context.moveTo(630,105);
                                context.lineTo(590,125);//止
                                context.moveTo(590,125);
                                context.lineTo(610,135);//止
                                context.strokeStyle = "#ccc"; // 设置线的颜色
                                context.stroke();
                                //A导线
                                if (j+1 == resganta[i].length) {
                                    context.beginPath();//开始路径绘制
                                    context.lineWidth=3;
                                    context.moveTo(600-resganta[i].length*100,135+resganta[i][j].length*10);
                                    context.lineTo(100,235+(resganta[i][j].length*10));//止0
                                    context.strokeStyle = "#333"; // 设置线的颜色
                                    context.stroke();
                                };
                            }else if(i==1){//B相上
                                context.beginPath();//开始路径绘制
                                context.lineWidth=1;
                                context.strokeStyle = "#333"; // 设置线的颜色
                                context.moveTo(440-x,360);//起
                                context.lineTo(440-x,360+(resganta[i][j].length*10));//止
                                context.stroke();
                                if (resganta[i][j][k]<=500) {
                                    EllipseOne(context,440-x,360+y,30,3);
                                    y+=10;
                                    context.fillStyle = "#d62323"; //故障绝缘子颜色
                                    context.fill();//填充
                                }else{
                                    EllipseOne(context,440-x,360+y,30,3);
                                    y+=10;
                                }
                                context.font="bold 20px Arial";
                                context.fillStyle="#333";
                                context.fillText("B"+(j+1),440-x,335);
                                //塔臂
                                context.beginPath();//开始路径绘制
                                context.lineWidth=3;
                                // B
                                context.moveTo(550,360);//起
                                context.lineTo(550-(resganta[i].length*100)-20,360);//止
                                context.moveTo(550,330);
                                context.lineTo(550-(resganta[i].length*100)-20,360);//止
                                context.moveTo(500,350);
                                context.lineTo(550-(resganta[i].length*100)-20,360);//止
                                context.moveTo(550,330);
                                context.lineTo(540,360);//止
                                context.moveTo(550,330);
                                context.lineTo(500,350);//止
                                context.moveTo(500,350);
                                context.lineTo(540,360);//止
                                context.strokeStyle = "#ccc"; // 设置线的颜色
                                context.stroke();
                                //B导线
                                if (j+1 == resganta[i].length) {
                                    context.beginPath();//开始路径绘制
                                    context.lineWidth=3;
                                    context.moveTo(530-(resganta[i].length*100),360+(resganta[i][j].length*10));
                                    context.lineTo(100,460+(resganta[i][j].length*10));//止
                                    context.strokeStyle = "#333"; // 设置线的颜色
                                    context.stroke();
                                };
                            }else if(i==2){//C相上
                                context.beginPath();//开始路径绘制
                                context.lineWidth=1;
                                context.strokeStyle = "#333"; // 设置线的颜色
                                context.moveTo(345-x,635);//起
                                context.lineTo(345-x,635+(resganta[i][j].length*10));//止
                                context.stroke();
                                if (resganta[i][j][k]<=500) {
                                    EllipseOne(context,345-x,635+y,30,3);
                                    y+=10;
                                    context.fillStyle = "#d62323"; //故障绝缘子颜色
                                    context.fill();//填充
                                }else{
                                    EllipseOne(context,345-x,635+y,30,3);
                                    y+=10;
                                }
                                context.font="bold 20px Arial";
                                context.fillStyle="#333";
                                context.fillText("C"+(j+1),345-x,610);
                                //塔臂
                                context.beginPath();//开始路径绘制
                                context.lineWidth=3;
                                context.moveTo(455,635);//起
                                context.lineTo(455-(resganta[i].length*100)-20,635);//止
                                context.moveTo(465,605);
                                context.lineTo(465-(resganta[i].length*100)-20,635);//止
                                context.moveTo(425,625);
                                context.lineTo(465-(resganta[i].length*100)-20,635);//止
                                context.moveTo(425,625);
                                context.lineTo(455,635);//止
                                context.moveTo(465,605);
                                context.lineTo(455,635);//止
                                context.moveTo(465,605);
                                context.lineTo(425,625);//止
                                context.strokeStyle = "#ccc"; // 设置线的颜色
                                context.stroke();
                                //C导线
                                if (j+1 == resganta[i].length) {
                                    context.beginPath();//开始路径绘制
                                    context.lineWidth=3;
                                    context.moveTo(445-(resganta[i].length*100),635+(resganta[i][j].length*10));
                                    context.lineTo(100,705+(resganta[i][j].length*10));//止
                                    context.strokeStyle = "#333"; // 设置线的颜色
                                    context.stroke();
                                };
                            }else if(i==3){//D相上
                                context.beginPath();//开始路径绘制
                                context.lineWidth=1;
                                context.strokeStyle = "#333"; // 设置线的颜色
                                context.moveTo(840+x,130);//起
                                context.lineTo(840+x,130+(resganta[i][j].length*10));//止
                                context.stroke();
                                if (resganta[i][j][k]<=500) {
                                    EllipseOne(context,840+x,130+y,30,3);
                                    y+=10;
                                    context.fillStyle = "#d62323"; //故障绝缘子颜色
                                    context.fill();//填充
                                }else{
                                    EllipseOne(context,840+x,130+y,30,3);
                                    y+=10;
                                }
                                context.font="bold 20px Arial";
                                context.fillStyle="#333";
                                context.fillText("D"+(j+1),840+x,105);
                                //塔臂
                                context.beginPath();//开始路径绘制
                                context.lineWidth=3;
                                context.moveTo(735,130);//起
                                context.lineTo(730+(resganta[i].length*100)+20,130);//止
                                context.moveTo(725,105);
                                context.lineTo(730+(resganta[i].length*100)+20,130);//止
                                context.moveTo(755,120);
                                context.lineTo(730+(resganta[i].length*100)+20,130);//止
                                context.moveTo(735,130);
                                context.lineTo(725,105);//止
                                context.moveTo(725,105);
                                context.lineTo(755,120);//止
                                context.moveTo(755,120);
                                context.lineTo(735,130);//止
                                //D导线
                                context.strokeStyle = "#ccc"; // 设置线的颜色
                                context.stroke();
                                //C导线
                                if (j+1 == resganta[i].length) {
                                    context.beginPath();//开始路径绘制
                                    context.lineWidth=3;
                                    context.moveTo(730+(resganta[i].length*100),130+(resganta[i][j].length*10));
                                    context.lineTo(730+(resganta[i].length*100)-100,180+(resganta[i][j].length*10));//止
                                    context.moveTo(730+(resganta[i].length*100),130+(resganta[i][j].length*10));
                                    context.lineTo(730+(resganta[i].length*100)+100,80+(resganta[i][j].length*10));//止
                                    context.strokeStyle = "#333"; // 设置线的颜色
                                    context.stroke();
                                }
                            }else if(i==4){//E相上
                                context.beginPath();//开始路径绘制
                                context.lineWidth=1;
                                context.strokeStyle = "#333"; // 设置线的颜色
                                context.moveTo(890+x,340);//起
                                context.lineTo(890+x,340+(resganta[i][j].length*10));//止
                                context.stroke();
                                if (resganta[i][j][k]<=500) {
                                    EllipseOne(context,890+x,340+y,30,3);
                                    y+=10;
                                    context.fillStyle = "#d62323"; //故障绝缘子颜色
                                    context.fill();//填充
                                }else{
                                    EllipseOne(context,890+x,340+y,30,3);
                                    y+=10;
                                }
                                context.font="bold 20px Arial";
                                context.fillStyle="#333";
                                context.fillText("E"+(j+1),890+x,315);
                                //塔臂
                                context.beginPath();//开始路径绘制
                                context.lineWidth=3;
                                context.moveTo(795,340);//起
                                context.lineTo(795+(resganta[i].length*100)+20,340);//止
                                context.moveTo(815,330);
                                context.lineTo(795+(resganta[i].length*100)+20,340);//止
                                context.moveTo(790,310);
                                context.lineTo(795+(resganta[i].length*100)+20,340);//止
                                context.moveTo(795,340);
                                context.lineTo(815,330);//止
                                context.moveTo(815,330);
                                context.lineTo(790,310);//止
                                context.moveTo(790,310);
                                context.lineTo(795,340);//止
                                context.strokeStyle = "#ccc"; // 设置线的颜色
                                context.stroke();
                                //E导线
                                if (j+1 == resganta[i].length) {
                                    context.beginPath();//开始路径绘制
                                    context.lineWidth=3;
                                    context.moveTo(795+(resganta[i].length*100),340+(resganta[i][j].length*10));
                                    context.lineTo(795+(resganta[i].length*100)-100,390+(resganta[i][j].length*10));//止
                                    context.moveTo(795+(resganta[i].length*100),340+(resganta[i][j].length*10));
                                    context.lineTo(795+(resganta[i].length*100)+100,290+(resganta[i][j].length*10));//止
                                    context.strokeStyle = "#333"; // 设置线的颜色
                                    context.stroke();
                                }
                            }else if(i==5){//F相上
                                context.beginPath();//开始路径绘制
                                context.lineWidth=1;
                                context.strokeStyle = "#333"; // 设置线的颜色
                                context.moveTo(965+x,600);//起
                                context.lineTo(965+x,600+(resganta[i][j].length*10));//止
                                context.stroke();
                                if (resganta[i][j][k]<=500) {
                                    EllipseOne(context,965+x,600+y,30,3);
                                    y+=10;
                                    context.fillStyle = "#d62323"; //故障绝缘子颜色
                                    context.fill();//填充
                                }else{
                                    EllipseOne(context,965+x,600+y,30,3);
                                    y+=10;
                                }
                                context.font="bold 20px Arial";
                                context.fillStyle="#333";
                                context.fillText("F"+(j+1),965+x,575);
                                //塔臂
                                context.beginPath();//开始路径绘制
                                context.lineWidth=3;
                                context.moveTo(870,600);//起
                                context.lineTo(865+(resganta[i].length*100)+10,600);//止
                                context.moveTo(885,590);
                                context.lineTo(865+(resganta[i].length*100)+10,600);//止
                                context.moveTo(860,570);
                                context.lineTo(865+(resganta[i].length*100)+10,600);//止
                                context.moveTo(865,600);
                                context.lineTo(885,590);//止
                                context.moveTo(885,590);
                                context.lineTo(860,570);//止
                                context.moveTo(860,570);
                                context.lineTo(870,600);//止
                                //C导线
                                context.strokeStyle = "#ccc"; // 设置线的颜色
                                context.stroke();
                                //C导线
                                if (j+1 == resganta[i].length) {
                                    context.beginPath();//开始路径绘制
                                    context.lineWidth=3;
                                    context.moveTo(870+(resganta[i].length*100),600+(resganta[i][j].length*10));
                                    context.lineTo(870+(resganta[i].length*100)-100,650+(resganta[i][j].length*10));//止
                                    context.moveTo(870+(resganta[i].length*100),600+(resganta[i][j].length*10));
                                    context.lineTo(870+(resganta[i].length*100)+100,550+(resganta[i][j].length*10));//止
                                    context.strokeStyle = "#333"; // 设置线的颜色
                                    context.stroke();
                                }
                            };
                        };
                        x+=100;
                    };
                };
            };
            //画椭圆方法
            function EllipseOne(context, x, y, a, b) {
                var step = (a > b) ? 1 / a : 1 / b;
                context.beginPath();
                context.moveTo(x + a, y);
                for(var i = 0; i < 2 * Math.PI; i += step) {
                    context.lineTo(x + a * Math.cos(i), y + b * Math.sin(i));
                }
                context.closePath();
                context.fillStyle = "#fff";
                context.fill();
            }
            context.font="bold 30px Arial";
            context.fillStyle="#333";
            context.fillText("回路"+loopnameb+"  "+number+"杆 塔 展 示",500,40);
    }
