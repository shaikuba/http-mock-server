(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-0e79409d"],{"1e9d":function(e,t,a){"use strict";var l=a("78ad"),i=a.n(l);i.a},"78ad":function(e,t,a){},"84b7":function(e,t,a){"use strict";a.r(t);var l=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",{staticClass:"crumbs"},[a("el-breadcrumb",{attrs:{separator:"/"}},[a("el-breadcrumb-item",[a("i",{staticClass:"el-icon-lx-cascades"}),e._v(" Mock List\n                ")])],1)],1),a("div",{staticClass:"container"},[a("div",{staticClass:"handle-box"},[a("el-button",{staticClass:"handle-del mr10",attrs:{type:"primary",icon:"el-icon-delete"},on:{click:e.delAllSelection}},[e._v("Delete\n                ")]),a("el-select",{staticClass:"handle-select mr10",attrs:{placeholder:"Method"},model:{value:e.query.criteria.requestMethod,callback:function(t){e.$set(e.query.criteria,"requestMethod",t)},expression:"query.criteria.requestMethod"}},[a("el-option",{attrs:{label:"Select",value:""}}),e._l(e.requestMethods,(function(e,t){return a("el-option",{key:t,attrs:{label:e,value:e}})}))],2),a("el-input",{staticClass:"handle-input mr10",attrs:{placeholder:"Request Url"},model:{value:e.query.criteria.requestUrl,callback:function(t){e.$set(e.query.criteria,"requestUrl",t)},expression:"query.criteria.requestUrl"}}),a("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:e.handleSearch}},[e._v("Search")])],1),a("el-table",{ref:"multipleTable",staticClass:"table",attrs:{data:e.tableData,border:"","header-cell-class-name":"table-header"},on:{"selection-change":e.handleSelectionChange}},[a("el-table-column",{attrs:{type:"selection",width:"55",align:"center"}}),a("el-table-column",{attrs:{prop:"id",label:"ID",width:"55",align:"center"}}),a("el-table-column",{attrs:{prop:"requestUrl",label:"Url"}}),a("el-table-column",{attrs:{prop:"requestMethod",label:"Method"}}),a("el-table-column",{attrs:{prop:"contentType",label:"Content-Type",align:"center"}}),a("el-table-column",{attrs:{prop:"queryString",label:"Query-String"}}),a("el-table-column",{attrs:{label:"Status",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-tag",{attrs:{effect:"dark",type:t.row.stateType}},[e._v(e._s(t.row.stateDesc))])]}}])}),a("el-table-column",{attrs:{label:"Operation",width:"180",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"text",icon:"el-icon-edit"},on:{click:function(a){return e.handleEdit(t.$index,t.row,t)}}},[e._v("\n                            Edit\n                        ")]),a("el-button",{staticClass:"red",attrs:{type:"text",icon:"el-icon-delete"},on:{click:function(a){return e.handleDelete(t.$index,t.row)}}},[e._v("\n                            Delete\n                        ")])]}}])})],1),a("div",{staticClass:"pagination"},[a("el-pagination",{attrs:{background:"",layout:"total, prev, pager, next","current-page":e.query.page.pageNumber+1,"page-size":e.query.page.pageSize,total:e.pageTotal},on:{"current-change":e.handlePageChange}})],1)],1),a("el-dialog",{attrs:{title:"Mock Edit",visible:e.editVisible,width:"60%"},on:{"update:visible":function(t){e.editVisible=t}}},[a("mock-create",{attrs:{isNew:!1,form:e.form},on:{"close-dialog":function(t){e.editVisible=!1,this.getData()}}})],1)],1)},i=[],n=a("c8c2"),r=["GET","POST","PUT","DELETE"],s=a("967e"),c={name:"basetable",data:function(){return{requestMethods:r,query:{criteria:{requestMethod:"",requestUrl:""},page:{pageNumber:0,pageSize:10},sort:{sortBy:"id",direction:"DESC"}},tableData:[],multipleSelection:[],editVisible:!1,pageTotal:0,form:{},idx:-1,id:-1}},components:{mockCreate:s["default"]},created:function(){this.getData()},methods:{getData:function(){var e=this;Object(n["c"])(JSON.stringify(this.query)).then((function(t){console.log(t),e.tableData=t.data.elements.map((function(e){return e.stateType="success",e.stateDesc="Active",e})),e.pageTotal=t.data.dataCount||10}))},handleSearch:function(){this.getData()},handleDelete:function(e,t){var a=this;this.$confirm("确定要删除吗？","提示",{type:"warning"}).then((function(){Object(n["b"])(t.id),a.$message.success("删除成功"),a.getData()})).catch((function(){}))},handleSelectionChange:function(e){console.log(e),this.multipleSelection=e},delAllSelection:function(){var e=this.multipleSelection.map((function(e){return e.id})).join();0!=e.length&&(Object(n["b"])(e),this.$message.error("删除了".concat(e)),this.getData()),this.multipleSelection=[]},handleEdit:function(e,t,a){console.log(a),this.idx=e,this.form=t,this.editVisible=!0},saveEdit:function(){this.editVisible=!1,this.$message.success("修改第 ".concat(this.idx+1," 行成功")),this.$set(this.tableData,this.idx,this.form)},handlePageChange:function(e){this.$set(this.query.page,"pageNumber",e-1),this.getData()}}},o=c,u=(a("1e9d"),a("2877")),d=Object(u["a"])(o,l,i,!1,null,"42171c33",null);t["default"]=d.exports}}]);