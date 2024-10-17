local voucherId = ARGV[1]
local userId = ARGV[2]
local orderId = ARGV[3]
local voucherStockKey = KEYS[1] .. voucherId
local stream_key = KEYS[2]
local orderSet = 'orderSet'

if (tonumber(redis.call('get', voucherStockKey))<=0) then
    return 1
end

if (redis.call('Sismember', orderSet, userId)==1) then
    return 2
end

redis.call('Sadd', orderSet, userId)
redis.call('Incrby', voucherStockKey, -1)

redis.call('xadd',stream_key,'*','userId',userId,'voucherId',voucherId,'id',orderId);

return 0
