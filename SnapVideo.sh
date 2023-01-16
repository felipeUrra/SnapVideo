cd /home/gemelos/Videos/videoJava
ffmpeg \
-loop 1 -t 5 -i image_01.jpg \
-loop 1 -t 5 -i image_02.jpg \
-loop 1 -t 5 -i image_03.jpg \
-loop 1 -t 5 -i image_04.jpg \
-loop 1 -t 5 -i image_05.jpg \
-i audio.mp3 \
-filter_complex \
"[1]format=yuva444p,fade=d=1:t=in:alpha=1,setpts=PTS-STARTPTS+4/TB[f0]; \
 [2]format=yuva444p,fade=d=1:t=in:alpha=1,setpts=PTS-STARTPTS+8/TB[f1]; \
 [3]format=yuva444p,fade=d=1:t=in:alpha=1,setpts=PTS-STARTPTS+12/TB[f2]; \
 [4]format=yuva444p,fade=d=1:t=in:alpha=1,setpts=PTS-STARTPTS+16/TB[f3]; \
 [0][f0]overlay[bg1];[bg1][f1]overlay[bg2];[bg2][f2]overlay[bg3]; \
 [bg3][f3]overlay,format=yuv420p[v]" -map "[v]" -map 5:a -shortest -movflags +faststart out.mp4