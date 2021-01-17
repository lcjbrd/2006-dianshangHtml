<template>
  <div>
    <h1>品牌信息</h1>
    <!--条件查询-->
    <div id="searchDiv">
      <el-form :inline="true" :model="param" class="demo-form-inline">

        <el-form-item label="名称">
          <el-input v-model="param.name" placeholder="名称"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="queryBrand(1)">查询</el-button>
          <el-button type="success" @click="addFormFlag=true">新增</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!--查询-->

    <!--查询-->

    <el-table
      :inline="true"
      :data="brand"
      height="250"
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column
        prop="name"
        label="品牌名称"
        width="130">
      </el-table-column>


      <el-table-column
        prop="imgpath"
        label="图片">
        <!-- 按文本处理   :formatter="formatImg"    -->
        <!-- 模板处理  html  -->
        <template slot-scope="scope">
          <!-- <img src="192.168.1.43:8080/imgFiless/f86a6cd6-a0e3-47a6-ba03-62a68ff41c99.gif"/>-->
          <img width="50px" :src="scope.row.imgpath"/>
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
        prop="bandDesc"
        label="品牌描述">
      </el-table-column>

      <el-table-column
        prop="ord"
        label="排序">
      </el-table-column>

      <el-table-column
        prop="createDate"
        label="创建时间">
      </el-table-column>

      <el-table-column
        prop="updateDate"
        label="修改时间">
      </el-table-column>



      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
    <el-dialog title="录入品牌信息" :visible.sync="addFormFlag">

      <el-form :model="addBrandFrom" ref="addBrandFrom" :rules="rule"  label-width="80px">

        <el-form-item label="名称" prop="name">
          <el-input v-model="addBrandFrom.name" autocomplete="off" ></el-input>
        </el-form-item>

        <!--图片上传-->

        <el-form-item label="图片">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8080/uploadPhoto"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
          >
            <img v-if="addBrandFrom.imgpath" :src="addBrandFrom.imgpath" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>

        <el-form-item label="是否显示" prop="isDel">
          <template>
            <el-radio v-model="addBrandFrom.isDel" :label="0">显示</el-radio>
            <el-radio v-model="addBrandFrom.isDel" :label="1">不显示</el-radio>
          </template>
        </el-form-item>

        <el-form-item label="品牌描述">
          <el-input type="textarea" v-model="addBrandFrom.bandDesc"></el-input>
        </el-form-item>

        <el-form-item label="排序" prop="price">
          <el-input v-model="addBrandFrom.ord"></el-input>
        </el-form-item>


      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="addFormFlag = false">取 消</el-button>
        <el-button type="primary" @click="addForm">确 定</el-button>
      </div>
    </el-dialog>

    <!--  修改的弹框   -->
    <el-dialog title="修改品牌信息" :visible.sync="updateFormFlag">

      <el-form :model="updateForm" ref="addBrandFrom" :rules="rule"  label-width="80px">

        <el-form-item label="名称" prop="name">
          <el-input v-model="updateForm.name" autocomplete="off" ></el-input>
        </el-form-item>

        <!--图片上传-->

        <!--图片上传-->
        <el-form-item label="图片">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8080/uploadPhoto"
            :show-file-list="false"
            :on-success="handleAvatarSuccess1"
            :before-upload="beforeAvatarUpload">
            <img v-if="updateForm.imgpath" :src="updateForm.imgpath" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>

        <el-form-item label="是否显示" prop="isDel">
          <template>
            <el-radio v-model="updateForm.isDel" :label="0">显示</el-radio>
            <el-radio v-model="updateForm.isDel" :label="1">不显示</el-radio>
          </template>
        </el-form-item>

        <el-form-item label="品牌描述">
          <el-input type="textarea" v-model="updateForm.bandDesc"></el-input>
        </el-form-item>

        <el-form-item label="排序" prop="price">
          <el-input v-model="updateForm.ord"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="updateFormFlag  = false">取 消</el-button>
        <el-button type="primary" @click="updateBrandForm">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
    export default {
        name: "Brand",
        data() {
            return{
                formLabelWidth: '120px',
                /*查询模块数据*/
                brand:[],
                param:{
                    size:4,
                    currPage:1,
                    name:'',
                },
                count:0,
                pageSizes:[2,4,6,8],

                /* 新增模块的数据  */
                types:[{"id":"1",value:"轿车"},{"id":"2",value:"SUV"},{"id":"3",value:"超跑"}],
                addFormFlag:false,
                addBrandFrom:{
                    name:"",
                    isDel:"",
                    imgpath:"",
                    ord:'',
                    bandDesc:'',
                },
                rule:{ //验证规则
                    brandname:[
                        { required: true, message: '请输入名称', trigger: 'blur' },
                        { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
                    ]

                },
                /*修改所需数据*/
                updateForm:{
                    id:'',
                    name:"",
                    isDel:"",
                    imgpath:"",
                    ord:'',
                    bandDesc:'',
                },
                addFormFlag:false,
                updateFormFlag:false,

            }
        },
        methods:{
            handleSelectionChange(val) {
                this.checkedbox = val;
            },
            queryBrand(currPage){
                var aa=this;
                this.$ajax.get("http://localhost:8080/api/brand/getData?"+this.$qs.stringify(this.param)).then(function (res) {
                    console.log(res)
                    /*获取数据*/
                    aa.brand=res.data.data.list;
                    /*获取总数据*/
                    aa.count=res.data.data.count;
                }).catch(function (res) {
                    alert("处理异常")
                })
            },

            sizeChange(val){//每页相似条数
                this.param.size=val;
                this.queryBrand();
            },
            pageChange(val){//当前页
                this.param.currPage=val;
                this.queryBrand();
            },

            addForm:function(){
                //取得验证结果

                this.$refs['addBrandFrom'].validate(res=>{

                    if(res==true){
                        //提交表单
                        this.$ajax.post("http://localhost:8080/api/brand/add",this.$qs.stringify(this.addBrandFrom)).then(res=>{
                            //关闭弹框
                            this.addFormFlag=false;
                            this.queryBrand(1);
                        }).catch(err=>console.log(err));
                    }
                });
            },
            handleEdit(index,row){
                //关闭弹框
                this.updateForm=row
                this.updateFormFlag=true;
                this.queryBrand(1);
            },
            updateBrandForm:function(){
                this.$ajax.post("http://localhost:8080/api/brand/update",this.$qs.stringify(this.updateForm)).then(res=>{
                    //关闭弹框
                    this.updateFormFlag=false;
                    this.queryBrand(1);
                }).catch(err=>console.log(err));
            },
            handleDelete:function(index,row){
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$ajax.delete("http://localhost:8080/api/brand/del?cid="+row.cid).then(res=>this.queryBrand(1)
                    ).catch(err=>console.log(err));
                });
            },

            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            handleAvatarSuccess(res, file) {
                this.addBrandFrom.imgpath = res.data;
                console.log(this.addBrandFrom.imgpath)
            },
            handleAvatarSuccess1(res, file) {
                this.updateForm.imgpath = res.data;
            },

            emitInput(val){
                this.$emit('input',val);
            }


        },
        created() {
            this.queryBrand(1);
        }

    }
</script>

<style scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
