# Chess - communication protocol

Every time that the client/server receive aright command, it will responds with `ACK` if the command exists, `INV` otherwise.

The end of the command must me use a `CRLF` (`\r\n`) separator, response also.

If the client responds with a value, the value will follow the `ACK` command with the line feed (`LF` or `\n`) as a separator.<br/>
For example: `ACK\n12345\r\n`.

### Kind of response:
- `ACK`: command works properly.
- `INV`: command doesn't exists.
- `ERR`: command format is not valid.

## From server to client

### `ping`
Just for test.<br/> 
If the communications works well the client will responds with `pong`.

#### Example
```
>> ping\r\n
<< ACK\n
<< pong\r\n
```


## From client to server

### `ping`
Just for test.<br/> 
If the communications works well the server will responds with `pong`.

#### Examples
```
>> ping\r\n
<< ACK\n
<< pong\r\n
```

### `retrive-players-names`
Retrive players names.<br/>
The players names will be retrived with the following format:
```
1;player-name-1;player-1-color\n
2;player-name-2;player-2-color\r\n
```

#### Examples
```
>> retrive-players-names\r\n
<< ACK\n
<< 1;foo;white\n
<< 2;bar;black\r\n
```

### `set-player-name '`*`name`*`'`
Set player name.<br/>
The username must be writed beetween single quotes.

#### Examples
```
>> set-player-name 'foo'\r\n
<< ACK\n
<< foo\r\n
```
```
>> set-player-name foo\r\n
<< ERR\r\n
```