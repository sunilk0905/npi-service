var dataCursor=db.getCollection('npi-data').find();
var providerCursor=db.getCollection('provider-npi').find();
var count=0;
while (dataCursor.hasNext()) {
   var providerCursor=db.getCollection('provider-npi').find();
   var flag=false;
   dataDocument=dataCursor.next();
    while(providerCursor.hasNext()){
        providerDocument=providerCursor.next();
        if(dataDocument.NPI==providerDocument.providerNPI){
            flag=true;
        }
            
    }
    if(!flag){
        count++
        printjson(dataDocument.NPI);
    }
   
}
print(count);