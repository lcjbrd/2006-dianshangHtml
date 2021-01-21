<template>
    <div style="margin-top: 50px">
      <el-steps :active="active" finish-status="success" align-center>
        <el-step title="基本信息"></el-step>
        <el-step title="商品属性"></el-step>
        <el-step title="商品图片"></el-step>
      </el-steps>

      <div v-if="active==0">
        <el-form :model="productForm"   ref="productForm" label-width="80px" style="width: 600px;margin: 0 auto;" size="small">
          <el-form-item label="商品名称" prop="name">
            <el-input v-model="productForm.name"></el-input>
          </el-form-item>

          <el-form-item label="标题" prop="title">
            <el-input v-model="productForm.title"></el-input>
          </el-form-item>

          <el-form-item label="品牌" prop="brandId">
            <el-select v-model="productForm.brandId" placeholder="请选择">
              <el-option v-for="b in brandDatas" :key="b.id" :label="b.name" :value="b.id"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="简介" prop="productdecs">
            <el-input type="textarea" v-model="productForm.productdecs"></el-input>
          </el-form-item>

          <el-form-item label="价格" prop="price">
            <el-input-number v-model="productForm.price" :precision="2" :step="0.1"></el-input-number>
          </el-form-item>

          <el-form-item label="库存" prop="stocks">
            <template>
              <el-input-number v-model="productForm.stocks" :step="10"></el-input-number>
            </template>
          </el-form-item>

          <el-form-item label="排序" prop="sortNum">
            <template>
              <el-input-number v-model="productForm.sortNum" :step="1"></el-input-number>
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
              <img v-if="productForm.imgPath" :src="productForm.imgPath" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>

          <el-form-item style="float: right">
            <el-button type="primary" size="medium" @click="next">下一步，填写商品属性</el-button>
          </el-form-item>
        </el-form>
      </div>

      <div v-if="active==1">


        <el-form :model="productForm2" label-width="80px" style="width: 600px;margin: 0 auto" size="small" class="demo-ruleForm">
          <el-form-item label="分类" prop="typeId">
            <el-select v-model="productForm2.typeId" placeholder="请选择" @change="getAttrData">
              <!--<el-option label="请选择" :value="-1"></el-option>-->
              <el-option v-for="b in types" :key="b.id" :label="b.name" :value="b.id"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item v-if="SKUData.length>0" label="商品规格" prop="name" label-width="80px" style="width: 600px;">


            <el-form-item v-for="a in  SKUData" :key="a.id" :label="a.nameCH">

              <!--  0 下拉框     1 单选框      2  复选框   3  输入框  -->
              <el-input v-if="a.type==3"></el-input>

              <el-select v-if="a.type==0"  placeholder="请选择">
                <el-option v-for="b in a.values" :key="b.id"  :label="b.nameCH" :value="b.id"></el-option>
              </el-select>

              <el-checkbox-group v-model="a.ckValues"  v-if="a.type==2" @change="skuChange">
                <el-checkbox v-for="b in a.values" :key="b.id" :label="b.nameCH" name="type"></el-checkbox>
              </el-checkbox-group>

              <el-radio-group  v-if="a.type==1"  >
                <el-radio v-for="b in a.values" :key="b.id" :label="b.nameCH"></el-radio>
              </el-radio-group>
            </el-form-item>
          </el-form-item>

          <el-table
            v-if="tableShow"
            :data="tableSkuData"
            style="width: 100%">
            <!--   动态展示列头  sku属性中文名 -->
            <el-table-column v-for="c in cols" :key="c.id" :label="c.nameCH" :prop="c.name">
            </el-table-column>

            <el-table-column
              label="库存"
              width="180">

              <template slot-scope="scope">
                <el-input v-model="scope.row.storcks"/>
              </template>

            </el-table-column>
            <el-table-column
              label="价格"
              width="180">
              <template slot-scope="scope">
                <el-input v-model="scope.row.pricess"/>
              </template>
            </el-table-column>
          </el-table>

  <br/>
  <br/>
      <el-form-item  v-if="attData.length>0" label="商品参数" prop="name" label-width="80px" style="width: 600px;" >

            <el-form-item v-for="a in  attData" :key="a.id" :label="a.nameCH">

             <!-- &lt;!&ndash;  0 下拉框     1 单选框      2  复选框   3  输入框  &ndash;&gt;-->
              <el-input v-if="a.type==3"></el-input>

              <el-select v-if="a.type==0" v-model="a.ckValues" placeholder="请选择" >
                <el-option v-for="b in a.values" :key="b.id"  :label="b.nameCH" :value="b.id"></el-option>
              </el-select>

              <el-radio-group  v-if="a.type==1" v-model="a.ckValues" label-width="80px">
                <el-radio v-for="b in a.values" :key="b.id" :label="b.nameCH"></el-radio>
              </el-radio-group>

              <el-checkbox-group  v-model="a.ckValues" v-if="a.type==2" >
                <el-checkbox v-for="b in a.values" :key="b.id" :label="b.nameCH" name="type"></el-checkbox>
              </el-checkbox-group>
            </el-form-item>

          </el-form-item>

          <el-form-item style="float: right">
            <el-button type="warning" size="medium" @click="active--">上一步，填写商品信息</el-button>
          <!--  <el-button type="primary" size="medium" @click="active++">下一步，填写图片信息</el-button>-->
            <el-button type="primary" size="medium" @click="add">完成提交</el-button>
          </el-form-item>
        </el-form>
      </div>

      <div v-if="active==2">
        第三步信息
          <el-form label-width="120px" style="width: 600px;margin: 0 auto;" size="small">
        <el-form-item style="float: right">
          <el-button type="warning" size="medium" @click="active--">上一步，填写商品属性</el-button>
          <el-button type="primary" size="medium" >完成提交</el-button>
        </el-form-item>
        </el-form>
      </div>

    </div>
</template>

<script>
    export default {
        name: "Addproduct",
        data() {
            return {
                active: 0,
                productForm:{
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
                /* 第二步相关的数据  */
                productForm2:{},
                ajaxTypeData:[], //接口返回的数据
                typeName:"", //递归拼接子节点的名称
                types:[  //分类的数据
                   ],
                attData:[],   //非sku的属性数据
                SKUData:[], //sku属性数据
                skuCK:[], //确定sku复选框绑定的变量名
                tableShow:false, //sku的table是否显示
                cols:[],//skutable的动态表头
                tableSkuData:[],//skutable的动态表头对应的表格数据
            }

        },
        methods: {
            add(){
                 this.productForm.typeId=this.productForm2.typeId;
                 console.log(this.productForm);
                console.log(this.attData);
                console.log(this.tableSkuData);
                //声明后台接参的atrr
                let  atrrs=[];
                for (let i = 0; i <this.attData.length ; i++) {
                    let  attData={};
                    attData[this.attData[i].name]=this.attData[i].ckValues;
                    atrrs.push(attData);
                }

                this.productForm.attr=JSON.stringify(atrrs);
                this.productForm.sku=JSON.stringify(this.tableSkuData); //传参是string   怎么将js json 转为字符串
                console.log(this.$qs.stringify(this.productForm));
                //发起请求  保存数据
                this.$ajax.post("http://localhost:8080/api/product/add",this.$qs.stringify(this.productForm)).then(res=>{
                    this.$message.success("添加成功");
                })
            },


            /* 笛卡尔积    */
            calcDescartes:function(array) {
                if (array.length < 2) return array[0] || [];
                return [].reduce.call(array, function (col, set) {
                    var res = [];
                    col.forEach(function (c) {
                        set.forEach(function (s) {
                            var t = [].concat(Array.isArray(c) ? c : [c]);
                            t.push(s);
                            res.push(t);
                        })
                    });
                    return res;
                });
            },
            //监听sku属性 改变事件
            skuChange:function(){
                //笛卡尔积的参数
                let  dkrj=[];
                //清空表格数据
                this.tableSkuData=[];
                //清空动态表头数据
                this.cols=[];
                console.log(this.SKUData);
                //判断是否要生成笛卡尔积
                let flag=true;
                for (let i = 0; i <this.SKUData.length ; i++) {
                    //将sku属性 放入动态表头中
                    this.cols.push({"id":this.SKUData[i].id,"nameCH":this.SKUData[i].nameCH,"name":this.SKUData[i].name});
                    //将此属性选中的选项值放入笛卡尔积参数中
                    //得到当前sku选中的值  颜色  选中的值 [红色,绿色]    尺寸 [l,x]
                    dkrj.push(this.SKUData[i].ckValues);

                    if(this.SKUData[i].ckValues.length==0){
                        flag=false;
                        break;
                    }
                }
                if(flag==true){
                    alert("生成笛卡尔积");
                    //生成table的数据  [[],[],[]]   [{},{},{}]
                    let  datas=this.calcDescartes(dkrj);
                    //遍历数据  [[红色,16g],[绿色,16g],[黑色，16g]]
                    for (let i = 0; i <datas.length ; i++) {
                        //遍历笛卡尔积 的每一项   [红色,16g]  cols:[{"id":1,"name": ,"nameCH"}]
                         let valuesAttr=datas[i];
                        let  jsonData={}; //笛卡尔积 转为json的对象
                        if(typeof valuesAttr=="object"){
                        for (let j = 0; j <valuesAttr.length ; j++) {
                            //获取数据的key
                            let  key=this.cols[j].name;
                            jsonData[key]=valuesAttr[j]

                        }
                        }else{
                            let key=this.cols[0].name;
                            jsonData[key]=valuesAttr;
                        }
                        this.tableSkuData.push(jsonData);
                    }
                    console.log(this.tableSkuData);
                    console.log(datas);


                }
                this.tableShow=flag;
            },


            /*  步骤条  下一页  */
            next() {
                // this.queryTypeData();
                if (this.active++ > 1) this.active = 0;
            },

            /*图片上传*/
            handleAvatarSuccess1(res, file) {
                this.productForm.imgPath = res.data;
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
            //初始化品牌数据
            queryBandData: function () {
                let param = {currPage: 1, size: 100000000};
                this.$ajax.post("http://localhost:8080/api/brand/getData", this.$qs.stringify(param)).then(res => {
                    this.brandDatas = res.data.data.list;
                });
            },
            //初始化分类数据
            queryTypeData: function () {
                    this.$ajax.get("http://localhost:8080/api/value/type").then(res => {
                    //console.log(res)
                    // [{id:1,"name":"",pid:2},{}]
                    this.ajaxTypeData = res.data.data.list;
                    //{"id":7,name:"分类/电子产品/手机"},
                    //先找到子节点的数据   this.types;
                    this.getChildrenType();
                    //遍历所有的子节点
                    for (let i = 0; i < this.types.length; i++) {
                        this.typeName = ""; // 全局变量   临时存 层级名称
                        //处理子节点的name属性
                        this.chandleName(this.types[i]);
                        //   a/b/c/f/d/e
                        //给name重新赋值
                        this.types[i].name = this.typeName.split("/").reverse().join("/").substr(0, this.typeName.length - 1);
                    }

                })
            }, //给我一个节点  得到层级name
            chandleName: function (node) {
                if (node.pid != 0) { //临界值
                    this.typeName += "/" + node.name;
                    //上级节点
                    for (let i = 0; i < this.ajaxTypeData.length; i++) {
                        if (node.pid == this.ajaxTypeData[i].id) {
                            this.chandleName(this.ajaxTypeData[i]);
                            break;
                        }
                    }

                } else {
                    this.typeName += "/" + node.name;
                }
            },
            //得到types的数据      遍历所有ajaxtypedata
            getChildrenType: function () {
                //遍历所有的节点数据
                for (let i = 0; i < this.ajaxTypeData.length; i++) {
                    let node = this.ajaxTypeData[i];
                    this.isChildrenNode(node);
                }
            },
            isChildrenNode: function (node) {
                let rs = true; //标示
                for (let i = 0; i < this.ajaxTypeData.length; i++) {
                    if (node.id == this.ajaxTypeData[i].pid) {
                        rs = false;
                        break;
                    }
                }
                if (rs == true) {
                    this.types.push(node);
                }
            },
            /*   根据typeid 查询属性数据和sku数据  */
            getAttrData: function (typeId) {
                this.SKUData = [];
                this.attData = [];
                this.tableShow=false;
                //发起请求 拿到属性数据
                this.$ajax.get("http://localhost:8080/api/attribute/queryAttrDataById?typeId=" + typeId).then(res => {
                    // 所有的属性数据
                    //console.log(res)
                    let attrDatas = res.data.data;

                    //判断分类是否有数据   更新 参数和规格
                    if (attrDatas.length > 0) {
                        //初始化  attData      SKUData
                        for (let i = 0; i < attrDatas.length; i++) {
                            //判断是否为sku属性
                            if (attrDatas[i].isSKU == 1) {

                                if (attrDatas[i].type != 3) {
                                    attrDatas[i].ckValues=[];
                                    //发起请求 查询此属性对应的选项值
                                    this.$ajax.get("http://localhost:8080/api/value/getAttvById?id=" + attrDatas[i].id).then(res => {
                                        //console.log(res)
                                        attrDatas[i].values = res.data.data;
                                        this.attData.push(attrDatas[i]);
                                    })
                                }else{
                                    this.attData.push(attrDatas[i]);
                                }

                            } else{
                                if (attrDatas[i].type != 3) {
                                    //发起请求 查询此属性对应的选项值
                                    this.$ajax.get("http://localhost:8080/api/value/getAttvById?id="+ attrDatas[i].id).then(res => {
                                        attrDatas[i].values = res.data.data;
                                        attrDatas[i].ckValues=[];

                                        this.SKUData.push(attrDatas[i]);
                                    })
                                }else{
                                    attrDatas[i].ckValues=[];
                                    this.SKUData.push(attrDatas[i]);
                                }
                            }
                        }
                        //console.log(this.attData)
                        //console.log(this.SKUData)
                    } else {
                        this.SKUData = [];
                        this.attData = [];
                    }
                })
            },
        },

        mounted() {
          this.queryBandData();
          this.queryTypeData();
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
