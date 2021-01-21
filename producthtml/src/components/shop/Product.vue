<template>
    <div>
      <!--查询-->

      <el-table
        :inline="true"
        :data="product"
        style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          prop="id"
          label="编号"
          width="80">
        </el-table-column>

        <el-table-column
          width="100px"
          prop="imgPath"
          label="图片">
          <!-- 按文本处理   :formatter="formatImg"    -->
          <!-- 模板处理  html  -->
          <template slot-scope="scope">
            <!-- <img src="192.168.1.43:8080/imgFiless/f86a6cd6-a0e3-47a6-ba03-62a68ff41c99.gif"/>-->
            <img width="50px" :src="scope.row.imgPath"/>
          </template>
        </el-table-column>

        <el-table-column
          prop="name"
          label="商品名称"
          width="130">
        </el-table-column>

        <el-table-column
          prop="productdecs"
          label="商品描述"
          width="100"
          show-overflow-tooltip>
        </el-table-column>


        <el-table-column
          prop="brandId"
          label="品牌"
          width="100">
        </el-table-column>

        <el-table-column
          prop="price"
          label="价格"
          width="100">
        </el-table-column>

        <el-table-column
          prop="stocks"
          label="库存"
          width="100">
        </el-table-column>

        <el-table-column
          prop="sortNum"
          label="排序"
          width="100">
        </el-table-column>

        <el-table-column label="是否启用" width="100" align="status" >
          <template slot-scope="scope">
            <el-switch
              @change="handleShowStatusChange( scope.row)"
              :active-value="0"
              :inactive-value="0"
              v-model="scope.row.isDel">
            </el-switch>
          </template>
        </el-table-column>

        <el-table-column
          prop="isDel"
          label="是否显示"
          width="80">
          <template slot-scope="scope">
            {{scope.row.isDel==0?"显示":scope.row.isDel==1?"不显示":"" }}
          </template>
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="success"
              @click="handleEdit(scope.$index, scope.row)">商品维护</el-button>
            <el-button
              size="mini"
              type="primary"
              @click="handleStu(scope.$index, scope.row)">属性维护</el-button>
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


      <!--修改商品基础信息-->
      <el-dialog title="修改商品基本信息" :visible.sync="updateFormFlag">

        <el-form :model="updateProductFrom" ref="UpdateProductFrom"   label-width="80px">
            <el-form-item label="商品名称" prop="name">
              <el-input v-model="updateProductFrom.name"></el-input>
            </el-form-item>

            <el-form-item label="标题" prop="title">
              <el-input v-model="updateProductFrom.title"></el-input>
            </el-form-item>

          <el-form-item label="品牌" prop="brandId">
            <el-select v-model="updateProductFrom.brandId" placeholder="请选择">
              <el-option v-for="b in brandDatas" :key="b.id" :label="b.name" :value="b.id"></el-option>
            </el-select>
          </el-form-item>

            <el-form-item label="简介" prop="productdecs">
              <el-input type="textarea" v-model="updateProductFrom.productdecs"></el-input>
            </el-form-item>

            <el-form-item label="价格" prop="price">
              <el-input-number v-model="updateProductFrom.price" :precision="2" :step="0.1"></el-input-number>
            </el-form-item>

            <el-form-item label="库存" prop="stocks">
              <template>
                <el-input-number v-model="updateProductFrom.stocks" :step="10"></el-input-number>
              </template>
            </el-form-item>

            <el-form-item label="排序" prop="sortNum">
              <template>
                <el-input-number v-model="updateProductFrom.sortNum" :step="1"></el-input-number>
              </template>
            </el-form-item>

            <!--图片上传-->
            <el-form-item label="图片">
              <el-upload
                class="avatar-uploader"
                action="http://localhost:8080/uploadPhoto"
                :show-file-list="false"
                :on-success="handleAvatarSuccess1"
                :before-upload="beforeAvatarUpload">
                <img v-if="updateProductFrom.imgPath" :src="updateProductFrom.imgPath" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-form>

        <div slot="footer" class="dialog-footer">
          <el-button @click="updateFormFlag = false">取 消</el-button>
          <el-button type="primary" @click="updateProduct">确 定</el-button>
        </div>
      </el-dialog>






    </div>
</template>

<script>
    export default {
        name: "product",
        data() {
            return {
                //修改基本商品
                updateFormFlag:false,
                updateProductFrom:{
                    name:"",
                    title:"",
                    brandId:"",
                    productdecs:"",
                    price:0,
                    stocks:0,
                    sortNum:0,
                    imgPath:""
                },
                brandDatas:[],

                //查询相关数据
                product:[],
                param:{
                    size:4,
                    currPage:1,
                    name:'',
                },
                count:0,
                pageSizes:[2,4,6,8],
            }
        },
        methods: {
            handleEdit(index,row){
                //关闭弹框
                this.updateProductFrom=row;
                this.updateFormFlag=true;

            },
            updateProduct(){
                this.$ajax.post("http://localhost:8080/api/product/update",this.$qs.stringify(this.updateProductFrom)).then(res=>{
                    //关闭弹框
                    this.updateFormFlag=false;
                    this.queryProduct();
                })
            },

            handleStu(index,row){

            },

            //初始化品牌数据
            queryBandData: function () {
                let param = {currPage: 1, size: 100000000};
                this.$ajax.post("http://localhost:8080/api/brand/getData", this.$qs.stringify(param)).then(res => {
                    this.brandDatas = res.data.data.list;
                });
            },
            /*图片上传*/
            handleAvatarSuccess1(res, file) {
                this.updateProductFrom.imgPath = res.data;
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 10;
                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 10MB!');
                }
                return isJPG && isLt2M;
            },

            handleShowStatusChange(row) {
                let isDel = row.isDel;
                this.$ajax.delete("http://localhost:8080/api/product/del?id="+row.id).then(res=>this.queryProduct(1)
                ).catch(err=>console.log(err));
            },
            handleSelectionChange(val) {
                this.checkedbox = val;
            },

            queryProduct(){
                var aa=this;
                this.$ajax.get("http://localhost:8080/api/product/getData?"+this.$qs.stringify(this.param)).then(function (res) {
                    console.log(res)
                    /*获取数据*/
                    aa.product=res.data.data.list;
                    /*获取总数据*/
                    aa.count=res.data.data.count;
                }).catch(function (res) {
                    alert("处理异常")
                })
            },

            sizeChange(val){//每页相似条数
                this.param.size=val;
                this.queryProduct();
            },
            pageChange(val){//当前页
                this.param.currPage=val;
                this.queryProduct();
            },
        },
        mounted() {
          this.queryProduct();
            this.queryBandData();
        }
    }
</script>

<style scoped>

</style>
