#version 330
#extension GL_ARB_separate_shader_objects : require

uniform sampler2D InSampler;

layout(std140) uniform SamplerInfo {
    vec2 OutSize;
    vec2 InSize;
};

layout(std140) uniform BlobsConfig {
    float Radius;
};

layout(location = 0) in vec2 texCoord;

layout(location = 0) out vec4 fragColor;

// Port of vanilla's old "blobs2" post effect (assets/minecraft/shaders/program/blobs2.fsh,
// used by kawa FocusShader before 1.21). It takes the max of the pixels inside a
// circular radius instead of averaging them, which produces the soft "blob" look.
void main() {
    vec2 oneTexel = 1.0 / InSize;
    vec4 maxVal = texture(InSampler, texCoord);

    float radius = max(Radius, 1.0);
    for (float u = 0.0; u <= radius; u += 1.0) {
        for (float v = 0.0; v <= radius; v += 1.0) {
            float weight = (sqrt(u * u + v * v) / radius) > 1.0 ? 0.0 : 1.0;

            vec4 s0 = texture(InSampler, texCoord + vec2(-u * oneTexel.x, -v * oneTexel.y));
            vec4 s1 = texture(InSampler, texCoord + vec2( u * oneTexel.x,  v * oneTexel.y));
            vec4 s2 = texture(InSampler, texCoord + vec2(-u * oneTexel.x,  v * oneTexel.y));
            vec4 s3 = texture(InSampler, texCoord + vec2( u * oneTexel.x, -v * oneTexel.y));

            vec4 tempMax = max(max(s0, s1), max(s2, s3));
            maxVal = mix(maxVal, max(maxVal, tempMax), weight);
        }
    }

    fragColor = vec4(maxVal.rgb, 1.0);
}