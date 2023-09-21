# Chess - communication protocol

Every time that the client/server receive aright command, it will responds with `ACK` if the command exists, `INV` otherwise.

The end of the command must me use a `CRLF` (`\r\n`) separator, response also.

If the client responds with a value, the value will follow the `ACK` command with the line feed (`LF` or `\n`) as a separator.<br/>
For example: `ACK\n12345\r\n`.

### Kind of response:
- `ACK`: command works properly.
- `INV`: command doesn't exists or is invalid.

## List of commands
- [From server to client](#fstc)
    - [`ping`](#fstc-ping)
- [From client to server](#fcts)
    - [`ping`](#fcts-ping)
    - [`ready-to-play`](#fcts-ready-to-play)
    - [`retrive-players-names`](#fcts-retrive-players-names)
    - [`set-player-name '`*`name`*`'`](#fcts-set-player-name)


## From server to client <a id="fstc"></a>

### `ping` <a id="fstc-ping"></a>
Just for test.<br/> 
If the communications works well the client will responds with `pong`.

#### Example
```
>> ping\r\n
<< ACK\n
<< pong\r\n
```


## From client to server <a id="fcts"></a>

### `ping` <a id="fcts-ping"></a>
Just for test.<br/> 
If the communications works well the server will responds with `pong`.

#### Examples
```
>> ping\r\n
<< ACK\n
<< pong\r\n
```

### `ready-to-play` <a id="fcts-ready-to-play"></a>
Retrive `TRUE` if everyone is connected and the game is prepared, else return `FALSE`.

#### Examples
```
>> ready-to-play\r\n
<< ACK\n
<< TRUE\rn
```

### `retrive-players-names` <a id="fcts-retrive-players-names"></a>
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

### `set-player-name '`*`name`*`'` <a id="fcts-set-player-name"></a>
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