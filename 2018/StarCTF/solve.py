# -*- coding: utf-8 -*-
import json, uuid, hashlib,rsa,pickle
import random,string

EMPTY_HASH = '0' * 64
DIFFICULTY = int('00000' + 'f' * 59, 16)

def hash(x):
    return hashlib.sha256(hashlib.md5(x).digest()).hexdigest()

def hash_reducer(x, y):
    return hash(hash(x) + hash(y))

def sign_input_utxo(input_utxo_id, privkey):
    return rsa.sign(input_utxo_id, privkey, 'SHA-1').encode('hex')

# 对 output 进行hash
def hash_utxo(utxo):
    return reduce(hash_reducer, [utxo['id'], utxo['addr'], str(utxo['amount'])])

def create_output_utxo(addr_to, amount):
    utxo = {'id': str(uuid.uuid4()), 'addr': addr_to, 'amount': amount}
    utxo['hash'] = str(hash_utxo(utxo))
    return utxo

# 对 transactions 进行hash
def hash_tx(tx):
    return reduce(hash_reducer, [
        reduce(hash_reducer, tx['input'], EMPTY_HASH),
        reduce(hash_reducer, [utxo['hash'] for utxo in tx['output']], EMPTY_HASH)
    ])

#对整个块 hash
def hash_block(block):
    return reduce(hash_reducer, [block['prev'], block['nonce'],
                                 reduce(hash_reducer, [tx['hash'] for tx in block['transactions']], EMPTY_HASH)])


bank_address = '990da5a0ccd7c610f45316d7de526f6e5a8eb0e7172c0049abd57cd77ade4c5daca55487024096ea727b2329bb26c481'
# 赎回的时候用我们的地址
my_address = '885a66372ff02f9697aabf2e280684066c3024385bdcf56cfc6da1d05d86cb12ebd867fe63e546b6850f1ef0c4dc6a4d'
privkey = "63636f70795f7265670a5f7265636f6e7374727563746f720a70300a28637273612e6b65790a507269766174654b65790a70310a635f5f6275696c74696e5f5f0a6f626a6563740a70320a4e7470330a5270340a284c32303938363636363237303537323732343838373030383730323036363732333136393238303137323032343534343938313032363737323634363839323131323835363837373831323038303136313038313237383037383133393437363432373033333630333437323031333135383938394c0a4936353533370a4c31323437333737323233393135363830323330323837373030303334343932303639353337373733333831323533343931333830333432353937303731363138333937373239373030383935393937393035363631393636323538343335343331303337323633303536353930303439333035334c0a4c31393137383739323838363234353439363834343231363735363631343935323633323537323137333231353630393833363838363031323638343937394c0a4c313039343236343139303430323932303737373532303136373639353739373330323733383036313232343832303334393033323139314c0a4c353235383136373931343030333637383634343037373138383139363835373738383736313431343239303231383932323838353831383333303436354c0a4c3135333439343532353236363234373332313437383539383337333835363937353531313130303535313434303736353830363338334c0a4c31353233353733313234323832353338343731393937363436383035313739363834393833383638343332333536373932373431343938363239393433334c0a7470350a622e"
tmp_privkey = pickle.loads(privkey.decode('hex'))   # 进行解码

### step1: 购买 100 tokens
# 创世块/上一块中的 output 里的ID
input_id = "d924f0db-0b46-4c93-8c8e-3d83291c83a2"
prev = "41bc26b103e779479118b5b315d944e5eb785563870acccb39bd71d8d417d89a"
signature = [sign_input_utxo(input_id, tmp_privkey)]

output = [create_output_utxo(bank_address,100)]

transactions = {
    "input": [input_id],
    "signature":signature,
    "output":output,
    "call_smart_contract":'buyTokens'
}

# 对 transactions 进行签名
hash_transactions = hash_tx(transactions)
transactions['hash'] = str(hash_transactions)

block = {
    "prev":prev,
    "transactions":[transactions],
    "nonce" : "step1"
}
block['hash'] = str(hash_block(block))
print(json.dumps(block))

### step2: 赎回
prev = "860aa2c3c1ab16ba0378498165b3a6a75662a2c4847cf9532a46b3257cc80d2d"
# 购买tokens时转给银行的 id
input_id = 'bf722709-bbea-406d-9486-4b259444dec8'
output = [create_output_utxo(my_address,100)]
transactions = {
    "input": [input_id],
    "signature":[],
    "output":output,
    "call_smart_contract":'withdraw'
}
# 对 transactions 进行签名
hash_transactions = hash_tx(transactions)
transactions['hash'] = str(hash_transactions)

block = {
    "prev":prev,
    "transactions":[transactions],
    "nonce" : "step2"
}
block['hash'] = str(hash_block(block))
print(json.dumps(block))

### step3: 转 0 给自己
prev = "861dadc278df24efb3e9dfab420a4137f7c933cd93d27a0980eb843735341b30"
# -100 tokennum 的 input_id
input_id = '1f7ab476-00dc-4cf2-b7e9-b82b8cb83bf4'
output = [create_output_utxo(my_address,0)]
signature = [sign_input_utxo(input_id, tmp_privkey)]

transactions = {
    "input": [input_id],
    "signature":signature,
    "output":output,
}
# 对 transactions 进行签名
hash_transactions = hash_tx(transactions)
transactions['hash'] = str(hash_transactions)

block = {
    "prev":prev,
    "transactions":[transactions],
    "nonce" : "step3"
}
block['hash'] = str(hash_block(block))
print(json.dumps(block))

### step4: 再购买 100 tokens
# 使用 上面赎回来的 input_id
input_id = "cc220143-4532-4bfc-9cea-0cae95231efb"
prev = "4149dafccdb394829022055ef59e572d558d7cdb62a4e4ea47b4e630ef4a2c43"
signature = [sign_input_utxo(input_id, tmp_privkey)]

output = [create_output_utxo(bank_address,100)]

transactions = {
    "input": [input_id],
    "signature":signature,
    "output":output,
    "call_smart_contract":'buyTokens'
}

# 对 transactions 进行签名
hash_transactions = hash_tx(transactions)
transactions['hash'] = str(hash_transactions)

block = {
    "prev":prev,
    "transactions":[transactions],
    "nonce" : "step4"
}
block['hash'] = str(hash_block(block))
print(json.dumps(block))

### step5: 现在有 200个tokens，直接赎回
prev = "cc396f7b9265af9ca39a54dad473cead6e1f5f46adcb357427b1f6b159c6f720"
# 一开始发行的银行里的 200中的input_id
input_id = 'be4fb3ca-4cdb-4bcc-8f5b-2cbe0b307e01'
output = [create_output_utxo(my_address,200)]   # 转200
transactions = {
    "input": [input_id],
    "signature":[],
    "output":output,
    "call_smart_contract":'withdraw'
}
# 对 transactions 进行签名
hash_transactions = hash_tx(transactions)
transactions['hash'] = str(hash_transactions)

block = {
    "prev":prev,
    "transactions":[transactions],
    "nonce" : "step5"
}
block['hash'] = str(hash_block(block))
print(json.dumps(block))