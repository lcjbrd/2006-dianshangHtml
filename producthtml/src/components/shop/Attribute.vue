<template>
    <div>
      <!--查询-->
      <div id="searchDiv">
        <el-form :inline="true" :model="param" class="demo-form-inline">

          <el-form-item label="名称">
            <el-input v-model="param.name" placeholder="名称"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="queryAttribute(1)">查询</el-button>
            <el-button type="success" @click="addFormFlag=true">新增</el-button>
          </el-form-item>
        </el-form>
      </div>

      <el-table
        :inline="true"
        :data="attribute"
        height="250"
        style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>

        <el-table-column
          prop="typeName"
          width="100"
          label="分类">
        </el-table-column>

        <el-table-column
          prop="name"
          label="属性英文名"
          width="130">
        </el-table-column>

        <el-table-column
          prop="nameCH"
          label="属性中文名"
          width="130">
        </el-table-column>

        <el-table-column
          prop="type"
          label="属性类型"
          width="130">
          <template slot-scope="scope">
            {{scope.row.type==0?"下拉框":scope.row.type==1?"单选框":scope.row.type==2?"复选框":scope.row.type==3?"输入框":""}}
          </template>
        </el-table-column>

        <el-table-column
          prop="isDel"
          label="是否显示"
          width="130">
          <template slot-scope="scope">
            {{scope.row.isDel==0?"显示":scope.row.isDel==1?"不显示":"" }}
          </template>
        </el-table-column>


        <el-table-column
          prop="createDate"
          label="创建时间">
        </el-table-column>

        <el-table-column
          prop="updateDate"
          label="修改时间">
        </el-table-column>


        <el-table-column
          label="操作"
          width="200">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button @click="ShowValueTable=true" type="text" size="small">属性值维护</el-button>
          </template>
        </el-table-column>



      </el-table>
      <!--分页-->
      <el-pagination
        layout="total, sizes, prev, pager, next, jumper"
        :total="count"
        :page-size="param.size"
        :page-sizes="pageSizes"
        @size-change="sizeChange"
        @current-change="pageChange">
      </el-pagination>


      <!--  新增的弹框   -->
      <el-dialog title="属性新增信息" :visible.sync="addFormFlag">



        <el-form :model="addAttriFrom" ref="addAttriFrom" label-width="80px">

          <el-form-item label="分类">
            <el-select v-model="addAttriFrom.typeId" placeholder="请选择分类">
              <el-option label="请选择" :value="-1"></el-option>
              <el-option v-for="item in types" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="英文名称" prop="name">
            <el-input v-model="addAttriFrom.name" autocomplete="off" ></el-input>
          </el-form-item>

          <el-form-item label="中文名称" prop="name">
            <el-input v-model="addAttriFrom.nameCH" autocomplete="off" ></el-input>
          </el-form-item>

          <el-form-item label="属性类型" prop="type">
            <template>
              <el-radio v-model="addAttriFrom.type" :label="0">下拉框</el-radio>
              <el-radio v-model="addAttriFrom.type" :label="1">单选框</el-radio>
              <el-radio v-model="addAttriFrom.type" :label="2">复选框</el-radio>
              <el-radio v-model="addAttriFrom.type" :label="3">输入框</el-radio>
            </template>
          </el-form-item>

          <el-form-item label="是否为Sku" prop="isSKU">
            <template>
              <el-radio v-model="addAttriFrom.isSKU" :label="0">是</el-radio>
              <el-radio v-model="addAttriFrom.isSKU" :label="1">不是</el-radio>
            </template>
          </el-form-item>

          <el-form-item label="是否显示" prop="isDel">
            <template>
              <el-radio v-model="addAttriFrom.isDel" :label="0">显示</el-radio>
              <el-radio v-model="addAttriFrom.isDel" :label="1">不显示</el-radio>
            </template>
          </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
          <el-button @click="addFormFlag = false">取 消</el-button>
          <el-button type="primary" @click="addForm">确 定</el-button>
        </div>
      </el-dialog>


      <el-dialog title="修改属性信息" :visible.sync="updateFormFlag">

        <el-form :model="updateAttriFrom" ref="addAttriFrom" label-width="80px">

          <el-form-item label="分类">
            <el-select v-model="updateAttriFrom.typeId" placeholder="请选择分类">
              <el-option label="请选择" :value="-1"></el-option>
              <el-option v-for="item in types" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="英文名称" prop="name">
            <el-input v-model="updateAttriFrom.name" autocomplete="off" ></el-input>
          </el-form-item>

          <el-form-item label="中文名称" prop="name">
            <el-input v-model="updateAttriFrom.nameCH" autocomplete="off" ></el-input>
          </el-form-item>

          <el-form-item label="属性类型" prop="type">
            <template>
              <el-radio v-model="updateAttriFrom.type" :label="0">下拉框</el-radio>
              <el-radio v-model="updateAttriFrom.type" :label="1">单选框</el-radio>
              <el-radio v-model="updateAttriFrom.type" :label="2">复选框</el-radio>
              <el-radio v-model="updateAttriFrom.type" :label="3">输入框</el-radio>
            </template>
          </el-form-item>

          <el-form-item label="是否为Sku" prop="isSKU">
            <template>
              <el-radio v-model="updateAttriFrom.isSKU" :label="0">是</el-radio>
              <el-radio v-model="updateAttriFrom.isSKU" :label="1">不是</el-radio>
            </template>
          </el-form-item>

          <el-form-item label="是否显示" prop="isDel">
            <template>
              <el-radio v-model="updateAttriFrom.isDel" :label="0">显示</el-radio>
              <el-radio v-model="updateAttriFrom.isDel" :label="1">不显示</el-radio>
            </template>
          </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
          <el-button @click="updateFormFlag  = false">取 消</el-button>
          <el-button type="primary" @click="updateForm">确 定</el-button>
        </div>
      </el-dialog>


      <!--  属性值的维护数据  -->

      <el-dialog title="属性值信息" :visible.sync="ShowValueTable">
        <el-table :data="gridData">
          <el-table-column property="date" label="属性" width="150"></el-table-column>
          <el-table-column property="name" label="属性值" width="200"></el-table-column>
          <el-table-column property="address" label="地址"></el-table-column>
        </el-table>
      </el-dialog>





    </div>
</template>

<script>
    export default {
        name: "Attribute",
        data() {
            return {
                attribute:[],
                param:{
                    size:4,
                    currPage:1,
                    name:'',
                },
                count:0,
                pageSizes:[2,4,6,8],
                addFormFlag:false,
                addAttriFrom:{
                    name:'',
                    nameCH:'',
                    type:'',
                    isSKU:'',
                    isDel:'',
                    typeId:-1
                },
                types:[
                    {"id":4,name:"分类/电子产品/手机"},
                    {"id":5,name:"分类/电子产品/电脑"},
                    {"id":11,name:"分类/服装/雨衣"},
                    {"id":19,name:"分类/服装/羽绒服"}],


                updateFormFlag:false,
                updateAttriFrom:{
                    id:'',
                    name:'',
                    nameCH:'',
                    type:'',
                    isSKU:'',
                    isDel:'',
                    typeId:-1
                },


                ShowValueTable:false
            }
        },
        methods: {
            handleSelectionChange(val) {
                this.checkedbox = val;
            },
            queryAttribute(currPage){
                var aa=this;
                this.$ajax.get("http://localhost:8080/api/attribute/queryAttributeData?"+this.$qs.stringify(this.param)).then(function (res) {
                    console.log(res)
                    /*获取数据*/
                    aa.attribute=res.data.data.list;
                    /*获取总数据*/
                    aa.count=res.data.data.count;
                }).catch(function (res) {
                    alert("处理异常")
                })
            },
            sizeChange(val){//每页相似条数
                this.param.size=val;
                this.queryAttribute();
            },
            pageChange(val){//当前页
                this.param.currPage=val;
                this.queryAttribute();
            },


            addForm:function(){
                //取得验证结果
                this.$refs['addAttriFrom'].validate(res=>{
                    if(res==true){
                        //提交表单
                        this.$ajax.post("http://localhost:8080/api/attribute/addAttribute",this.$qs.stringify(this.addAttriFrom)).then(res=>{
                            //关闭弹框
                            this.addFormFlag=false;
                            this.queryAttribute(1);
                        }).catch(err=>console.log(err));
                    }
                });
            },


            handleEdit(index,row){
                //关闭弹框
                console.log(row)
                this.updateAttriFrom=row;
                this.updateAttriFrom.typeId=row.typeId;
                this.updateFormFlag=true;
                this.queryAttribute(1);
            },
            updateForm:function(){
                this.$ajax.post("http://localhost:8080/api/attribute/updateAttribute",this.$qs.stringify(this.updateAttriFrom)).then(res=>{
                    //关闭弹框
                    this.updateFormFlag=false;
                    this.queryAttribute(1);
                }).catch(err=>console.log(err));
            },



        },


        mounted() {

        },
        created() {
            this.queryAttribute(1);
        }
    }
</script>

<style scoped>

</style>
