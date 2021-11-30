  <%--
    Created by IntelliJ IDEA.
    User: saberQueen
    Date: 2021/11/29
    Time: 18:39
    To change this template use File | Settings | File Templates.
  --%>
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <!--优化
  1.在页面加载后自动加入省份表
  -->
  <html>
  <center>
    <head>
      <title>ajax_级联查询</title>
      <script type="text/javascript" src="js/jquery.js"></script>
      <script type="text/javascript">
        //URL样式；/sf/id=3  →返回省份下拉列表；
        function loadAJX(){
          var count = 0;
          $.ajax({
            url : "ceshi",
            dataType : "json",
            error : function(){
              alert("失败");

            },
            success : function(resp){
              // alert(1);
              // alert(1);
              //如果已经加载完毕，则不用加载了；

              if(count===0){
                for(var i=0;i<resp.length;i++){
                  var x = "<option value="+ resp[i]["id"]+">"+ resp[i]["name"]+"</option>";
                  $("#province").append(x);
                  //<option value=1> 河北 </option>";
                }
                count++;

              }

            }

          })
        }

        $(function(){
          loadAJX();
          $("#btn").click(function(){
            // alert(100);
                 loadAJX();
          })


              //如果被选择河北，则根据其id查询数据库，返回id为1的所有城市并且在下拉列表中展示；
          //onchange句柄；
          var cou =0;
          $("#province").change(function(){
            var idNum = $("#province>option:selected").val();
            // alert(idNum);
            // alert(10000);

            $.ajax({
              url: "CITY",
              data :{
                id : idNum
              },
              dataType : "json",
              success : function(response){
                // $("#first").empty();
                    // alert(9999);
                // <option value="0"> 请下拉选择城市   </option>;
                if(cou++!=0){
                  $("#city").empty();
                  var x = "<option value='0' id='first'> 请下拉选择城市   </option>";
                  $("#city").append(x);

                }
                  for(var i=0;i<response.length;i++){
                    var x = "<option value="+ response[i]["provinceID"]+">"+ response[i]["cityName"]+"</option>";
                    $("#city").append(x);
                    //<option value=1> 河北 </option>";
                  }


              }

            })
          })






        })

        // $.ajax(
        //         { url: "dianji",//这里不用加？号，自动添加；
        //           data : {
        //             number : $("#number").val()
        //           },
        //           dataType : "json",
        //           success : function(resp){
        //             $("#provin").val(resp["name"]) ;
        //             $("#provinsimple").val(resp["jiancheng"]) ;
        //             $("#provin_city").val(resp["shenghui"]) ;
        //
        //           }
        //         })









      </script>

    </head>
    <body>
    <div>
      <p> 省市级联查询 </p>
      <select id="province">
        <option value="0"> 请下拉选择省份   </option>
      </select>
      <br/><br/>
       <select id="city">
          <option value="0" id="first"> 请下拉选择城市   </option>
      </select><br/><br/>

    </div>

    </body>
  <center><input type="button"  value="加载省份"  id="btn"  /></center>
  </center>
  </html>
