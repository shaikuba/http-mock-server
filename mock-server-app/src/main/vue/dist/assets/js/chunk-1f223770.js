(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-1f223770"],{1099:function(e,t,n){"use strict";var a=n("d50e"),r=n.n(a);r.a},"5d58":function(e,t,n){e.exports=n("d8d6")},"67bb":function(e,t,n){e.exports=n("f921")},"84b7":function(e,t,n){"use strict";n.r(t);var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("div",{staticClass:"crumbs"},[n("el-breadcrumb",{attrs:{separator:"/"}},[n("el-breadcrumb-item",[n("i",{staticClass:"el-icon-lx-cascades"}),e._v(" Mock List ")])],1)],1),n("div",{staticClass:"container"},[n("div",{staticClass:"handle-box"},[n("el-button",{staticClass:"handle-del mr10",attrs:{type:"primary",icon:"el-icon-delete"},on:{click:e.delAllSelection}},[e._v("Delete ")]),n("el-select",{staticClass:"handle-select mr10",attrs:{placeholder:"Method"},model:{value:e.query.criteria.requestMethod,callback:function(t){e.$set(e.query.criteria,"requestMethod",t)},expression:"query.criteria.requestMethod"}},[n("el-option",{attrs:{label:"Select",value:""}}),e._l(e.requestMethods,(function(e,t){return n("el-option",{key:t,attrs:{label:e,value:e}})}))],2),n("el-input",{staticClass:"handle-input mr10",attrs:{placeholder:"Request Url, Title or Tags"},model:{value:e.query.criteria.requestUrl,callback:function(t){e.$set(e.query.criteria,"requestUrl",t)},expression:"query.criteria.requestUrl"}}),n("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:e.handleSearch}},[e._v("Search")])],1),n("el-table",{ref:"multipleTable",staticClass:"table",attrs:{"default-sort":{prop:"id",order:"descending"},data:e.tableData,border:"","header-cell-class-name":"table-header"},on:{"sort-change":e.changeSort,"selection-change":e.handleSelectionChange}},[n("el-table-column",{attrs:{type:"selection",width:"55",align:"center"}}),n("el-table-column",{attrs:{sortable:"",prop:"id",label:"ID",width:"55",align:"center"}}),n("el-table-column",{attrs:{prop:"title",label:"Title"}}),n("el-table-column",{attrs:{prop:"requestUrl",label:"Url"}}),n("el-table-column",{attrs:{sortable:"",prop:"requestMethod",label:"Method"}}),n("el-table-column",{attrs:{prop:"contentType",label:"Content-Type",align:"center"}}),n("el-table-column",{attrs:{prop:"tags",label:"Tags"},scopedSlots:e._u([{key:"default",fn:function(t){return e._l(t.row.tags,(function(t){return n("el-tag",{key:t,attrs:{size:"small"}},[e._v("\n                        "+e._s(t)+"\n                    ")])}))}}])}),n("el-table-column",{attrs:{sortable:"",prop:"status",label:"Status",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{attrs:{round:"",type:t.row.stateType},on:{click:function(n){return e.changeStatus(t.$index,t.row)}}},[e._v(e._s(t.row.stateDesc))])]}}])}),n("el-table-column",{attrs:{label:"Operation",width:"180",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{attrs:{type:"text",icon:"el-icon-edit"},on:{click:function(n){return e.handleEdit(t.$index,t.row,t)}}}),n("el-button",{staticClass:"red",attrs:{type:"text",icon:"el-icon-delete"},on:{click:function(n){return e.handleDelete(t.$index,t.row)}}}),n("el-button",{attrs:{type:"text",icon:"el-icon-document-copy"},on:{click:function(n){return e.handleCopy(t.$index,t.row)}}})]}}])})],1),n("div",{staticClass:"pagination"},[n("el-pagination",{attrs:{background:"",layout:"total, prev, pager, next","current-page":e.query.page.pageNumber+1,"page-size":e.query.page.pageSize,total:e.pageTotal},on:{"current-change":e.handlePageChange}})],1)],1),n("el-dialog",{attrs:{title:e.mockDialog.title,visible:e.editVisible,width:"60%"},on:{"update:visible":function(t){e.editVisible=t}}},[n("mock-create",{attrs:{isNew:!1,type:e.mockDialog.type,form:e.form},on:{"close-dialog":function(t){e.editVisible=!1,e.getData()}}})],1)],1)},r=[],i=(n("55dd"),n("96cf"),n("3b8d")),o=n("c8c2"),s=n("60fe"),l=n("967e"),c=n("5d58"),u=n.n(c),p=n("67bb"),d=n.n(p);function h(e){return h="function"===typeof d.a&&"symbol"===typeof u.a?function(e){return typeof e}:function(e){return e&&"function"===typeof d.a&&e.constructor===d.a&&e!==d.a.prototype?"symbol":typeof e},h(e)}function f(e){return f="function"===typeof d.a&&"symbol"===h(u.a)?function(e){return h(e)}:function(e){return e&&"function"===typeof d.a&&e.constructor===d.a&&e!==d.a.prototype?"symbol":h(e)},f(e)}function b(e){if("object"==f(e)&&e)return e;if("string"==typeof e)try{var t=JSON.parse(e);if("object"==f(t)&&t)return t}catch(n){console.log(e)}return{}}function g(e){if("object"==f(e)&&e){var t=JSON.stringify(e);return JSON.parse(t)}return e}var m={name:"basetable",data:function(){return{requestMethods:s["b"],query:{criteria:{requestMethod:"",requestUrl:""},page:{pageNumber:0,pageSize:10},sort:{sortBy:"id",direction:"DESC"}},tableData:[],multipleSelection:[],editVisible:!1,pageTotal:0,form:{},idx:-1,id:-1,mockDialog:{title:"Mock Edit",type:"edit"}}},components:{mockCreate:l["default"]},created:function(){this.getData()},methods:{getData:function(){var e=this;Object(o["c"])(JSON.stringify(this.query)).then((function(t){e.tableData=t.data.elements.map((function(e){return"n"!=e.status?(e.stateType="success",e.stateDesc="Active"):(e.stateType="info",e.stateDesc="Inactive"),e})),e.pageTotal=t.data.dataCount||10}))},handleSearch:function(){this.$set(this.query.page,"pageNumber",0),this.getData()},changeStatus:function(){var e=Object(i["a"])(regeneratorRuntime.mark((function e(t,n){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return n.status="a"==n.status?"n":"a",e.next=3,Object(o["a"])(n).then((function(e){console.log(e)}));case 3:this.getData();case 4:case"end":return e.stop()}}),e,this)})));function t(t,n){return e.apply(this,arguments)}return t}(),handleDelete:function(e,t){var n=this;this.$confirm("确定要删除吗？","提示",{type:"warning"}).then(Object(i["a"])(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,Object(o["b"])(t.id);case 2:n.$message.success("删除成功"),n.getData();case 4:case"end":return e.stop()}}),e)})))).catch((function(){}))},handleSelectionChange:function(e){console.log(e),this.multipleSelection=e},delAllSelection:function(){var e=this;this.$confirm("确定要删除选中项吗？","提示",{type:"warning"}).then(Object(i["a"])(regeneratorRuntime.mark((function t(){var n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(n=e.multipleSelection.map((function(e){return e.id})).join(),0==n.length){t.next=6;break}return t.next=4,Object(o["b"])(n);case 4:e.$message.error("删除了".concat(n)),e.getData();case 6:e.multipleSelection=[];case 7:case"end":return t.stop()}}),t)}))))},handleEdit:function(e,t,n){console.log(n),this.idx=e,this.form=g(t),this.form.description=b(this.form.description),this.editVisible=!0,this.mockDialog.title="Mock Edit",this.mockDialog.type="edit"},handleCopy:function(e,t,n){console.log(n),this.idx=e,this.form=g(t),this.form.title="Copied From ".concat(this.form.title),this.form.description=b(this.form.description),this.editVisible=!0,this.mockDialog.title="Mock Copy",this.mockDialog.type="copy"},handlePageChange:function(e){this.$set(this.query.page,"pageNumber",e-1),this.getData()},changeSort:function(e){console.log(e),this.query.sort.sortBy="requestMethod"==e.prop?"request_method":e.prop,this.query.sort.direction=null==e.order||-1!=e.order.indexOf("desc")?"DESC":"ASC",this.handleSearch()},showInput:function(){var e=this;this.inputVisible=!0,this.$nextTick((function(t){e.$refs.saveTagInput.$refs.input.focus()}))}}},y=m,k=(n("1099"),n("2877")),v=Object(k["a"])(y,a,r,!1,null,"756fc911",null);t["default"]=v.exports},d50e:function(e,t,n){}}]);