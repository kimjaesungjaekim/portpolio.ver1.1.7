/**
 * 웹 크롤링 
 */


$(function(){
    fn_newsCrawling();
  });



function fn_newsCrawling(){

    $.ajax({
        type: "GET",
        url: "/news",
        dataType: 'json',
        success: function (data) {

            let news = data.newsList;
            let divTag ="";

            if(news.length != 0){

                divTag +=`
                    <div>
                        <table class= "table">
                            <colgroup>
                                <col width="30%" />
                                <col width="70%" />
                            </colgroup>
                            <tr>
                                <th>이미지</th>
                                <th>제목</th>
                            </tr>
                `;

                $.each(news , function(index,v){
                    if(v.subject != null){
                        divTag+=`
                                <tr>
                                    <td><a href="${v.url}"><img src="${v.image}"></a></td>
                                    <td><a href="${v.url}"><span>${v.subject}</span></a></td>
                                    </a>
                                </tr>
                        `;
                    }
                })
                divTag+=` </table> </div> `;
                $('#news').html(divTag);    
            }else{
                divTag+=`
                    <div>
                        <table class= "table">
                        <tr>
                            <th>이미지</th>
                            <th>제목</th>
                        </tr>
                        <tr colspan="2">
                            <td>내역없음</td>
                        </tr>
                        </table>
                    </div>
                `;
                $('#news').html(divTag);    
            }
        },
        error: function () {
        }
    });

}  