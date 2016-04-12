/**
 * Created by kenny on 6/9/14.
 */

window.mainDrugList = null;
window.batchList = null;
function loadStockData() {
    $.ajax({
        url: 'reference/json/stocks/listReferences/' + window.accountID + '/' + window.user_id,
        headers: {
            "Content-Type": "application/json"
        },
        type: 'POST',
        success: function (data) {
            if(data.batchList && data.drugList) {
                window.mainDrugList = data.drugList;
                window.batchList = data.batchList;
                $('#sheetNo').html(data.sheetNo);
                createHTML();
            }

        },
        error: function () {
            alert(data);
        }
    });
}

function createHTML() {
    for(var i = 0; i < window.batchList.length; i++) {
        var html = getStockRow(i, window.batchList[i]);
        $('#stockTakingTable > tbody:last').append(html);
    }
    window.print();
}

function getStockRow(index, batch) {
    var html = '<tr id="stockTakingTable-' + index + '" index="' + index + '"><td><label name="index" class="index">' + (index + 1 ) + '</label></td><td style="min-width: 200px;">';

    for(var i = 0; i < window.mainDrugList.length; i++) {
        if( window.mainDrugList[i].id == batch.drugId) {
            html += '' + window.mainDrugList[i].name + '';
            break;
        }
    }
    var date = new Date(batch.expiry_date);
    var prettyDate =(date.getMonth()+1) + '/' + date.getDate() + '/' +
        date.getFullYear();
    html += '</select></td><td style="width: 50px;text-align: right;">' + batch.stockInBatch + '</td>' +
        '<td style="width: 120px;text-align: right;">' + batch.batch_no + '</td><td style="width: 120px;text-align: right;">' + prettyDate + '</td>' +
        '<td style="width: 120px;"></td>' +
        '</tr>';
    return html;
}
