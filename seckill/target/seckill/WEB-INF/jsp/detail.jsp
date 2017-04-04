<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情页</title>
    <%@ include file="common/head.jsp"%>
</head>
<body>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h1>${seckill.name}</h1>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">
                <!--显示time图标-->
                <span class="glyphicon glyphicon-time"></span>
                <!--展示倒计时-->
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>库存</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>创建时间</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${seckill.name}</td>
                        <td>${seckill.number}</td>
                        <td>
                            <fmt:formatDate value="${seckill.startTime}" pattern="yy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${seckill.endTime}" pattern="yy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${seckill.createTime}" pattern="yy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<!--弹出层 输入电话-->
<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"></span>秒杀电话:
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey"
                               placeholder="填写手机号" class="form-control"/>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <!--验证信息-->
                <span id="killPhoneMessage" class="glyphicon"></span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    Submit
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
<!--countdown照着视频上面的才行 自己去cdn搜索的都不行-->
<script src="/resources/script/seckill.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        //使用el表达式传入参数
        seckill.detail.init({
            seckillId:${seckill.seckillId},
            startTime:${seckill.startTime.time},
            endTime:${seckill.endTime.time}
        });
    });
</script>
</html>
