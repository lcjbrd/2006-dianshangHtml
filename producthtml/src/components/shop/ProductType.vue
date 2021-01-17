<template>
  <div>
    <el-tree
      :data="data"
      ref="tree"
      show-checkbox
      node-key="id"
      @node-click="clickNode"
      :check-on-click-node="true"
      :default-expand-all="true"
      :props="defaultProps">
    </el-tree>

    <el-row v-show="isShow">
      <el-button type="primary" @click="dialogFormVisible = true,toadd()" round>新增</el-button>
      <el-button type="success" @click="dialogFormVisible1 = true,toupdate()" round>修改</el-button>
      <el-button type="warning" @click="del" round>批量删除</el-button>
    </el-row>

    <el-dialog title="子节点" :visible.sync="dialogFormVisible" >
      <el-form :model="chil">
        <el-form-item label="pid" :label-width="formLabelWidth">
          <el-input v-model="chil.pid" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="chil.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="是否上架" prop="isDel" :label-width="formLabelWidth">
          <template>
            <el-radio v-model="chil.isDel" :label="0">上架</el-radio>
            <el-radio v-model="chil.isDel" :label="1">下架</el-radio>
          </template>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false,addType()">确 定</el-button>
      </div>
    </el-dialog>



    <el-dialog title="子节点" :visible.sync="dialogFormVisible1" >
      <el-form :model="chil1">
        <el-form-item label="pid" :label-width="formLabelWidth">
          <el-input v-model="chil1.pid" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="chil1.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="是否上架" prop="isDel" :label-width="formLabelWidth">
          <template>
            <el-radio v-model="chil1.isDel" :label="0">上架</el-radio>
            <el-radio v-model="chil1.isDel" :label="1">下架</el-radio>
          </template>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible1 = false,updateType()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    export default {
        name: 'productType',
        data() {
            return {
                chil:{
                    id:"",
                    name:'',
                    pid:'',
                    isDel:'',
                },
                chil1:{
                    id:"",
                    name:'',
                    pid:'',
                    isDel:'',
                },
                dialogFormVisible:false,
                dialogFormVisible1:false,
                formLabelWidth:'120px',
                checkedNdoe:{
                    id:'',
                    name:'',
                    pid:'',
                },
                isShow:false,
                data: [],
                defaultProps: {
                    dialogFormVisible:true,
                    children: 'children',
                    label: 'name'
                }
            };
        },
        created:function() {
            this.query()
        },
        methods: {
            clickNode(data,none){
                this.checkedNdoe=data;
                this.isShow=true
                var pid=data.pid;
                this.$refs.tree.setChecked(pid,false,false);
            },
            query(){
                var aa = this;
                this.$ajax.get("http://localhost:8080/api/type/getData").then(function (res) {
                    // console.log(res)
                    aa.data=res.data.data.typeList;
                }).catch(function (res) {
                    alert("处理异常")
                })
            },
            addType(){
                var aa = this;
                this.$ajax.post("http://localhost:8080/api/type/add",this.$qs.stringify(this.chil)).then(function (res) {
                    aa.query();
                })

            },
            updateType(){
                 console.log(this.chil1)
                var aa = this;
                this.$ajax.post("http://localhost:8080/api/type/update",this.$qs.stringify(this.chil1)).then(function (res) {
                    aa.query();
                })
            },
            toadd(){
                this.chil.name="";
                this.dialogFormVisible=true;
                this.chil.pid=this.checkedNdoe.id;
            },
            toupdate(){
                console.log(this.checkedNdoe)
                this.dialogFormVisible1=true;
                this.chil1=this.checkedNdoe;
            },
            del(){
                var checkedbox = this.$refs.tree.getCheckedNodes()
                var idList=[];
                if (checkedbox!=null&&checkedbox.length>0){
                    for (let i = 0; i < checkedbox.length; i++) {
                        idList.push(checkedbox[i].id)
                    }
                    var aa = this;
                    this.axios.post("/productApi/tree/delete.do",this.$qs.stringify({"idList":idList},{indices:false})).then(function (res) {
                        if (res.data.code==200){
                            aa.query();
                        }

                    })
                }else{
                    this.$message('请选择要删除的节点');
                }
            }

        }
    }
</script>

<style scoped>

</style>
