<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="Keywords" content="五子棋">
    <meta name="Description" content="人机对战五子棋">
    <title>人工智能五子棋</title>
    <link href="${pageContext.request.contextPath }/static/source/css/chess.css" rel='stylesheet' type='text/css'/>
    <jsp:include page="view/include/commonfile.jsp"/>
    <script src="${pageContext.request.contextPath }/static/plugins/sockjs/sockjs.js"></script>
</head>
<body>
<jsp:include page="view/include/header.jsp"/>
<div class="am-cf admin-main">
    <jsp:include page="view/include/sidebar.jsp"/>
    <div class="admin-content">
        <h1 class="biaoti">人机对战五子棋</h1>
        <input type="button" class="button3" id="button3" onclick="withdraw()" value="悔棋"/>
        <input type="button" class="button2" onclick="newgame()" value="重新开始"/>
        <div class="am-panel am-pa el-default" style="float:right;width: 20%;">
            <div class="am-panel-hd">
                <h3 class="am-panel-title">人机对战</h3>
            </div>
            <ul class="am-list am-list-static am-list-striped">
                <input type="button" class="button4" value="你为先手"/>
            </ul>
        </div>
        <canvas class="canvasplace" id="canvas" width="450" height="450"></canvas>
    </div>
    <a href="#" class="am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}">
        <span class="am-icon-btn am-icon-th-list"></span></a>
    <jsp:include page="view/include/footer.jsp"/>
</div>
<script>

    var chess = document.getElementById("canvas");
    var context = chess.getContext("2d");
    context.strokeStyle = "#d3d3d3"

    var me = true;
    var over = false;

    var logo = new Image();
    logo.src = "${ctx}/static/source/img/ricepaper.png";
    logo.onload = function () {
        context.drawImage(logo, 0, 0, 450, 450);
        drawChessBoard();
    }
    // 棋盘坐标

    var chessBoard = [];

    for (var i = 0; i < 15; i++) {
        chessBoard[i] = [];
        for (var j = 0; j < 15; j++) {
            chessBoard[i][j] = 0;
        }
    }

    //绘制棋盘
    var drawChessBoard = function () {
        context.beginPath();
        for (var i = 0; i < 15; i++) {
            context.moveTo(15 + i * 30, 15);
            context.lineTo(15 + i * 30, 435);
            context.stroke();
            context.moveTo(15, 15 + i * 30);
            context.lineTo(435, 15 + i * 30);
            context.stroke();
        }
        context.closePath();

    }

    //画棋子
    var onStep = function (i, j, me) {
        console.log("实下的位置:" + i + j);

        //将棋子push到list
        chessList.push({
            x: i,
            y: j,
            color: chessList.length % 2 === 0 ? 'black' : 'white'
        })

        console.log(chessList);

        context.beginPath();
        context.arc(15 + i * 30, 15 + j * 30, 13, 0, 2 * Math.PI);
        var gradient = context.createRadialGradient(15 + i * 30 - 2, 15 + j * 30 + 2, 13, 15 + i * 30 - 2, 15 + j * 30 - 2, 0);
        if (me) {
            gradient.addColorStop(0, "#0A0A0A");
            gradient.addColorStop(1, "#636766");
        } else {
            gradient.addColorStop(0, "#D1D1D1");
            gradient.addColorStop(1, "#F9F9F9");
        }

        context.fillStyle = gradient;
        context.closePath();
        context.fill();
    }

    var chessList = [];
    //定义一个落子的方法
    chess.onclick = function (e) {
        if (over) {
            return false;
        }
        if (!me) {
            return false;
        }

        var x = e.offsetX;
        var y = e.offsetY;
        var i = Math.floor(x / 30);
        var j = Math.floor(y / 30);

        if (chessBoard[i][j] === 0) {
            onStep(i, j, me);
            chessBoard[i][j] = 2; // 黑棋

            for (var k = 0; k < count; k++) {
                if (wins[i][j][k]) {
                    myWin[k]++;
                    computerWin[k] = 6;
                    if (myWin[k] === 5) {
                        context.beginPath();
                        context.arc(15 + i * 30, 15 + j * 30, 13, 0, 2 * Math.PI);
                        context.fillStyle = 'red';
                        context.closePath();
                        context.fill();
                        window.alert("你赢了!");
                        over = true;
                        //
                        $.ajax({
                            //请求方式
                            type: "GET",
                            //请求地址
                            url: "insertOrUpdateScore",
                            //数据，json字符串
                            data: {userId:${userid}},
                            //请求成功
                            success: function (result) {
                                console.log(result);
                            },
                            //请求失败，包含具体的错误信息
                            error: function (e) {
                                console.log(e.status);
                                console.log(e.responseText);
                            }
                        });
                    }
                }
            }
            if (!over) {
                me = !me;
                computerAI();
            }
        }

    }

    var wins = [];
    var myWin = [];
    var computerWin = [];

    for (var i = 0; i < 15; i++) {
        wins[i] = [];
        for (var j = 0; j < 15; j++) {
            wins[i][j] = [];
        }
    }

    var count = 0;

    //竖线
    for (var i = 0; i < 15; i++) {
        for (var j = 0; j < 11; j++) {
            for (var k = 0; k < 5; k++) {
                wins[i][j + k][count] = true;
            }
            count++;
        }
    }

    //横线
    for (var i = 0; i < 11; i++) {
        for (var j = 0; j < 15; j++) {
            for (var k = 0; k < 5; k++) {
                wins[i + k][j][count] = true;
            }
            count++;
        }
    }

    //斜线
    for (var i = 0; i < 11; i++) {
        for (var j = 0; j < 11; j++) {
            for (var k = 0; k < 5; k++) {
                wins[i + k][j + k][count] = true;
            }
            count++;
        }
    }

    //反斜线
    for (var i = 0; i < 11; i++) {
        for (var j = 14; j > 3; j--) {
            for (var k = 0; k < 5; k++) {
                wins[i + k][j - k][count] = true;
            }
            count++;
        }
    }

    for (var i = 0; i < count; i++) {
        myWin[i] = 0;
        computerWin[i] = 0;
    }
    console.log(count);

    function computerAI() {
        var myScore = [];
        var computerScore = [];
        var max = 0;
        var maxX = 0;
        var maxY = 0;

        for (var i = 0; i < 15; i++) {
            myScore[i] = [];
            computerScore[i] = [];
            for (var j = 0; j < 15; j++) {
                myScore[i][j] = 0;
                computerScore[i][j] = 0;
            }
        }

        for (var i = 0; i < 15; i++) {
            for (var j = 0; j < 15; j++) {
                if (chessBoard[i][j] === 0) {
                    for (var k = 0; k < count; k++) {
                        if (wins[i][j][k]) {
                            if (myWin[k] === 1) {
                                myScore[i][j] += 200;
                            } else if (myWin[k] === 2) {
                                myScore[i][j] += 400;
                            } else if (myWin[k] === 3) {
                                myScore[i][j] += 2000;
                            } else if (myWin[k] === 4) {
                                myScore[i][j] += 10000;
                            }
                        }

                        if (wins[i][j][k]) {
                            if (computerWin[k] === 1) {
                                computerScore[i][j] += 211;
                            } else if (computerWin[k] === 2) {
                                computerScore[i][j] += 420;
                            } else if (computerWin[k] === 3) {
                                computerScore[i][j] += 2100;
                            } else if (computerWin[k] === 4) {
                                computerScore[i][j] += 20000;
                            }
                        }
                    }
                    if (myScore[i][j] > max) {
                        max = myScore[i][j];
                        maxX = i;
                        maxY = j;
                    } else if (myScore[i][j] === max) {
                        if (computerScore[i][j] > computerScore[maxX][maxY]) {
                            maxX = i;
                            maxY = j;
                        }
                    }
                    if (computerScore[i][j] > max) {
                        max = computerScore[i][j];
                        maxX = i;
                        maxY = j;
                    } else if (computerScore[i][j] === max) {
                        if (myScore[i][j] > myScore[maxX][maxY]) {
                            maxX = i;
                            maxY = j;
                        }
                    }
                }
            }
        }
        onStep(maxX, maxY, me);
        chessBoard[maxX][maxY] = 1;
        for (var k = 0; k < count; k++) {
            if (wins[maxX][maxY][k]) {
                computerWin[k]++;
                myWin[k] = 6;
                if (computerWin[k] === 5) {
                    context.beginPath();
                    context.arc(15 + maxX * 30, 15 + maxY * 30, 13, 0, 2 * Math.PI);
                    context.fillStyle = 'red';
                    context.closePath();
                    context.fill();
                    window.alert("计算机赢了!");
                    over = true;
                }
            }
        }
        if (!over) {
            me = !me;
            console.log(me);
        }
    }

    function withdraw() {
        console.log("悔棋");
        var Plist = [];
        var Plist1 = [];
        Plist = chessList.pop();
        Plist1 = chessList.pop();
        console.log(Plist);
        console.log(Plist1);
        chessBoard[Plist.x][Plist.y] = 0;
        chessBoard[Plist1.x][Plist1.y] = 0;
        //myWin[k]--;
        // if (Plist.color === 'white') {
        //     for (var k = 0; k < count; k++) {
        //         if (wins[Plist.x][Plist.y][k]) {
        //             myWin[k]--;
        //         }
        //     }
        // } else {
        //     for (var k = 0; k < count; k++) {
        //         if (wins[Plist.x][Plist.y][k]) {
        //             computerWin[k]--;
        //         }
        //     }
        // }
        console.log(chessList);
        context.clearRect(0, 0, 450, 450);
        context.drawImage(logo, 0, 0, 450, 450);
        drawChessBoard();
        chessList.forEach(function (item) {
            context.beginPath();
            context.arc(30 * item.x + 15, 30 * item.y + 15, 13, 0, 2 * Math.PI, false);
            var gradient = context.createRadialGradient(15 + item.x * 30 - 2, 15 + item.y * 30 + 2, 13, 15 + item.x * 30 - 2, 15 + item.y * 30 - 2, 0);
            if (item.color === 'black') {
                gradient.addColorStop(0, "#0A0A0A");
                gradient.addColorStop(1, "#636766");
                context.fillStyle = gradient;
            } else {
                gradient.addColorStop(0, "#D1D1D1");
                gradient.addColorStop(1, "#F9F9F9");
                context.fillStyle = gradient;
            }
            context.fill();

        })

    }

    // var revertFlag = false;
    // //悔棋事件
    // function withdraw() {
    //     window.confirm("确定要悔棋吗？");
    //     var Plist = [];
    //     Plist = chessList.pop();
    //     if (!over && !revertFlag) {
    //         context.clearRect(Plist.x * 30, Plist.y * 30, 30, 30);
    //         chessBoard[Plist.x][Plist.y] = 0;
    //         if (!me) {
    //             for (var k = 0; k < count; k++) {
    //                 if (wins[Plist.x][Plist.y][k]) {
    //                     myWin[k]--;
    //                 }
    //             }
    //         } else {
    //             for (var k = 0; k < count; k++) {
    //                 if (wins[Plist.x][Plist.y][k]) {
    //                     computerWin[k]--;
    //                 }
    //             }
    //         }
    //         me = !me;
    //         revertFlag = true;
    //     }
    // }
    function newgame() {
        if (confirm("开始新的游戏？")) {
            location.reload();
        }
    }


</script>
</body>
</html>